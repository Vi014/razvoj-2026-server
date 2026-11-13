package com.example.lmsserver.kontroleri.response;

import com.example.lmsserver.klase.enumi.LoginUloga;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Instant istice;
    private LoginUloga uloga;
}
