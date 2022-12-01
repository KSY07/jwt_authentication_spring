package com.scarf.fracas.authsvr.Entity;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

public class RefreshTokenRequest {
    @NotBlank
    private @Getter @Setter String refreshToken;    
}
