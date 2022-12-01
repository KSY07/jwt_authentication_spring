package com.scarf.fracas.authsvr.Entity;

import java.util.HashSet;
import java.util.Set;
import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "scarf_users")
@NoArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter Long id;

    @NotBlank
    @Size(max = 20)
    private @Getter @Setter String userId;

    @NotBlank
    @Size(max = 120)
    private @Getter @Setter String password;

    @NotBlank
    @Size(max = 20)
    private @Getter @Setter String name;

    @NotBlank
    @Email
    private @Getter @Setter String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private @Getter @Setter Set<RoleEntity> roles = new HashSet<>();

    private @Getter @Setter String company;

    private @Getter @Setter Integer toolTeamId;

    private @Getter @Setter Integer baseCode;

    @CreatedDate
    private @Getter @Setter Timestamp signupTimestamp;

    private @Getter @Setter Integer userFlag;

    private @Getter @Setter Integer lineFlag;

    private @Getter @Setter String projectCode;

    public User(String userId, String password,
                String name, String email,
                String company, Integer toolTeamId,
                Integer baseCode, Integer userFlag,
                Integer lineFlag, String projectCode) {
                    this.userId = userId;
                    this.password = password;
                    this.name = name;
                    this.email = email;
                    this.company = company;
                    this.toolTeamId = toolTeamId;
                    this.baseCode = baseCode;
                    this.userFlag = userFlag;
                    this.lineFlag = lineFlag;
                    this.projectCode = projectCode;
                }


}
