/** 
 * 
 * @version : 1.0.0
 * @author : 김세영
 * @param: Security의 UserDetails 인터페이스 구현체
 * @see : build()Custom UserDetails 객체를 만들어 반환
 *
*/




package com.scarf.fracas.authsvr.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scarf.fracas.authsvr.Entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ScarfRUserDetails implements UserDetails{

    private static final long serialVersionUID = 1L;

    private @Getter Long id;

    @JsonIgnore
    private String password;

    private String userId;

    private @Getter @Setter String email;

    private @Getter @Setter String name;

    private @Getter @Setter String projectCode;

    private @Getter @Setter String company;

    private Collection<? extends GrantedAuthority> authorities;

    public static ScarfRUserDetails build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

        return new ScarfRUserDetails(
            user.getId(),
            user.getPassword(),
            user.getUserId(),
            user.getEmail(),
            user.getName(),
            user.getProjectCode(),
            user.getCompany(),
            authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        ScarfRUserDetails user = (ScarfRUserDetails) o;

        return Objects.equals(id, user.id);
    }
    
}
