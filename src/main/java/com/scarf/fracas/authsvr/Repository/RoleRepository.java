package com.scarf.fracas.authsvr.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scarf.fracas.authsvr.Entity.RoleEntity;

import java.util.Optional;

import com.scarf.fracas.authsvr.Entity.Constant.Role;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(Role name);
    
}
