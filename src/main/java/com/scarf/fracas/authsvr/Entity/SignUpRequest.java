package com.scarf.fracas.authsvr.Entity;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignUpRequest {
    
    @NotBlank
    @Size(max = 20)
    private String userId;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 40)
    private String password;

    private String projectCode;

    @NotBlank
    @Email
    private String email;
    
    private Set<String> role;

    private String company;

    private Integer toolTeamId;

    private Integer baseCode;

    private Integer userFlag;

    private Integer lineFlag;

}
