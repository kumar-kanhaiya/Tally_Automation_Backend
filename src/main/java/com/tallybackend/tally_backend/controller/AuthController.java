package com.tallybackend.tally_backend.controller;

import com.tallybackend.tally_backend.dto.LoginRequestDto;
import com.tallybackend.tally_backend.dto.LoginResponseDto;
import com.tallybackend.tally_backend.dto.SignUpRequestDto;
import com.tallybackend.tally_backend.dto.SignUpResponseDto;
import com.tallybackend.tally_backend.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        return ResponseEntity.ok(authService.signUp(signUpRequestDto));
    }

}
