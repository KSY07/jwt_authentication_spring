/**
 * 
 * @author: 김세영
 * @version: 1.0.0
 * @param : 인증담당 Controller (Auth,RefreshToken 구현)
 */



package com.scarf.fracas.authsvr.Controller;

import javax.validation.Valid;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scarf.fracas.authsvr.Entity.LoginRequest;
import com.scarf.fracas.authsvr.Entity.LoginResponse;
import com.scarf.fracas.authsvr.Entity.MessageResponse;
import com.scarf.fracas.authsvr.Entity.RefreshToken;
import com.scarf.fracas.authsvr.Entity.RefreshTokenRequest;
import com.scarf.fracas.authsvr.Entity.RefreshTokenResponse;
import com.scarf.fracas.authsvr.Entity.SignUpRequest;
import com.scarf.fracas.authsvr.Entity.User;
import com.scarf.fracas.authsvr.Entity.Constant.Role;
import com.scarf.fracas.authsvr.Exception.RefreshTokenException;
import com.scarf.fracas.authsvr.Entity.RoleEntity;
import com.scarf.fracas.authsvr.Repository.RoleRepository;
import com.scarf.fracas.authsvr.Repository.UserRepository;
import com.scarf.fracas.authsvr.Service.RefreshTokenService;
import com.scarf.fracas.authsvr.Service.ScarfRUserDetails;
import com.scarf.fracas.authsvr.Utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    public final AuthenticationManager authenticationManager;

    public final UserRepository userRepository;

    public final RoleRepository roleRepository;

    public final PasswordEncoder passwordEncoder;

    public final JwtUtils jwts;

    public final RefreshTokenService refreshTokenService;
    
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        //authentication 객체 생성 (Request로부터 Id 와 Password를 가져와
        // UsernamePasswordAuthenticationToken 하여 인증 객체 생성)
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUserId(), request.getPassword())
        );
        //생성된 authentication 객체를 SecurityContextHolder에 저장(인증)
        //이때, DAOAuthenticationProvider가 작동
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //AuthToken 발행
        String authtoken = jwts.generateAuthToken(authentication);
        
        //authentication 에 매핑된 userDetails 객체의 정보를 가져옴
        ScarfRUserDetails userDetails = (ScarfRUserDetails) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream() // 
        .map(item -> item.getAuthority()) // stream으로 받아온 List에 권한 부여 mapping
        .collect(Collectors.toList()); // collector에 담은후 List화

        /////////////RefreshToken Generate///////////////////////////
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new LoginResponse(
                            authtoken,
                            refreshToken.getToken(),
                            userDetails.getUsername(),
                            userDetails.getEmail(),
                            userDetails.getProjectCode(),
                            roles)
                            );
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                        String token = jwts.generateTokenFromUsername(user.getUserId());
                        return ResponseEntity.ok(new RefreshTokenResponse(token, requestRefreshToken));
                })
                    .orElseThrow(() -> new RefreshTokenException(requestRefreshToken, "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public String logout() {
        return "This is Logout Page";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest request) {
        
        if (userRepository.existsByUserId(request.getUserId())) {
            return ResponseEntity
            .badRequest()
            .body(new MessageResponse("Error: UserId is already taken!"));
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
            .badRequest()
            .body(new MessageResponse("Error: Email is already taken!"));
        }

        User user = new User(
            request.getUserId(),
            passwordEncoder.encode(request.getPassword()),
            request.getName(),
            request.getEmail(),
            request.getCompany(),
            request.getToolTeamId(),
            request.getBaseCode(),
            request.getUserFlag(),
            request.getLineFlag(),
            request.getProjectCode()
        );

        Set<String> strRoles = request.getRole();
        Set<RoleEntity> roles = new HashSet<>();

        if(strRoles == null) {
            RoleEntity userRole = roleRepository.findByName(Role.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException("Error"));

            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" :
                        RoleEntity adminRole = roleRepository.findByName(Role.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error"));

                        roles.add(adminRole);

                        break;
                    
                    case "pm" :
                        RoleEntity pmRole = roleRepository.findByName(Role.ROLE_PM)
                        .orElseThrow(() -> new RuntimeException("Error"));
                        roles.add(pmRole);
                        break;

                    case "rams" :
                        RoleEntity ramsRole = roleRepository.findByName(Role.ROLE_RAMS)
                        .orElseThrow(() -> new RuntimeException("Error"));
                        roles.add(ramsRole);
                        break;
                    default:
                        RoleEntity userRole = roleRepository.findByName(Role.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error"));
                        roles.add(userRole);
                        break;
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User Registered"));
    }
}
