package com.company.SecureTrack_backend.controller;

import com.company.SecureTrack_backend.service.AuthService;
import com.company.SecureTrack_backend.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

//    @PostMapping("/google")
//    public ResponseEntity<ResponseDto<Map<String, String>>> googleAuth(@RequestBody GoogleTokenDto dto) {
//        // dto.getIdToken() yoki dto.getAccessToken()
//        return authService.authenticateWithGoogle(dto);
//    }
//
//    @GetMapping("/oauth2/callback")
//    public ResponseEntity<ResponseDto<Map<String, String>>> oauth2Callback(
//            @RequestParam String code) {
//        return authService.handleGoogleCallback(code);
//    }


    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout(@RequestParam UUID userId) {
        return authService.logout(userId);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto<String>> refreshAccessToken(@RequestParam UUID id, @RequestParam String refreshToken) {
        return authService.refreshAccessToken(id, refreshToken);
    }
}
