package com.scarf.fracas.authsvr.Entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private String authtoken;
    private String refreshToken;
    private String userId;
    private String email;
    private String projectCode;
    private List<String> roles;
}
