/**
 * @version: 1.0.0
 * @author: 김세영
 * @return: ScarfRUserDetails
 * @exception: Username NotFound, DB Disconnect
 */


package com.scarf.fracas.authsvr.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scarf.fracas.authsvr.Entity.User;
import com.scarf.fracas.authsvr.Repository.UserRepository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;


@Service
public class ScarfRUserDetailsService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException{
        User user = userRepository.findByUserId(userId) //Repository에서 userId로 user객체 생성
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found" + userId)); //찾지못하면 예외

        return ScarfRUserDetails.build(user); //user -> ScarfRUserDetails를 변환 및 반환. 
    }
    
}
