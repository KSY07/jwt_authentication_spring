package com.scarf.fracas.authsvr.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenResponse {
    private @Getter @Setter String authToken;
    private @Getter @Setter String refreshToken;
    private @Getter @Setter String tokenType = "Bearer";
    
    public RefreshTokenResponse(String authToken, String refreshToken) {
        this.authToken = authToken;
        this.refreshToken = refreshToken;
    }
}
