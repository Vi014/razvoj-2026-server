package com.example.lmsserver.kontroleri.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String lozinka;
    @NotBlank
    private String uloga;
}
