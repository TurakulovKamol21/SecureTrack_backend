package com.company.SecureTrack_backend.service;

//import com.company.SecureTrack_backend.dto.GoogleTokenDto;
import com.company.SecureTrack_backend.util.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public interface AuthService {



    ResponseEntity<ResponseDto<Void>> logout(UUID userId);

    ResponseEntity<ResponseDto<String>> refreshAccessToken(UUID id,String refreshToken);

//    ResponseEntity<ResponseDto<Map<String, String>>> authenticateWithGoogle(GoogleTokenDto dto);
//
//    ResponseEntity<ResponseDto<Map<String, String>>> handleGoogleCallback(String code);

}

