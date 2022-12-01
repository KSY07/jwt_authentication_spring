package com.scarf.fracas.authsvr.Entity;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

public class LoginRequest {

    @NotBlank
    private @Getter String userId;

    @NotBlank
    private @Getter String password;
    
}
