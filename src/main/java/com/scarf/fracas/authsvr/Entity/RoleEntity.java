package com.scarf.fracas.authsvr.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.scarf.fracas.authsvr.Entity.Constant.Role;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "scarf_users_roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private @Getter @Setter Role name;

    public RoleEntity() {

    }

    public RoleEntity(Role name) {
        this.name = name;
    }
    
}
