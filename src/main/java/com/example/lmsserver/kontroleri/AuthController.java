package com.example.lmsserver.kontroleri;

import com.example.lmsserver.kontroleri.request.LoginRequest;
import com.example.lmsserver.kontroleri.response.LoginResponse;
import com.example.lmsserver.servisi.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/auth")
public class AuthController {
    //final AuthService authService;

    @PostMapping(path="/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return null;
    }
}