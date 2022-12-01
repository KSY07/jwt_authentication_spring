package com.scarf.fracas.authsvr.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.scarf.fracas.authsvr.Entity.RefreshToken;
import com.scarf.fracas.authsvr.Entity.User;

@Repository
@EnableJpaRepositories
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
    Optional<RefreshToken> findByToken(String token);


    @Modifying
    Integer deleteByUser(User user);

}
