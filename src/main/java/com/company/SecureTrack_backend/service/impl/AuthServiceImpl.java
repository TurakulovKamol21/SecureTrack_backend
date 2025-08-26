package com.company.SecureTrack_backend.service.impl;

import com.company.SecureTrack_backend.config.jwt.JwtProvider;
//import com.company.SecureTrack_backend.dto.GoogleOAuth2Client;
//import com.company.SecureTrack_backend.dto.GoogleTokenDto;
//import com.company.SecureTrack_backend.dto.GoogleTokenResponse;
//import com.company.SecureTrack_backend.dto.GoogleUserInfo;
import com.company.SecureTrack_backend.model.User;
import com.company.SecureTrack_backend.model.enums.Role;
import com.company.SecureTrack_backend.repository.UserRepository;
import com.company.SecureTrack_backend.service.AuthService;
import com.company.SecureTrack_backend.service.RefreshTokenService;
import com.company.SecureTrack_backend.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
//    private final GoogleOAuth2Client googleClient;
    private final RefreshTokenService refreshTokenService;


    @Override
    public ResponseEntity<ResponseDto<String>> refreshAccessToken(UUID userId, String refreshToken) {
        if (!refreshTokenService.isValid(userId, refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseDto.<String>builder()
                            .message("Refresh token invalid or expired")
                            .httpStatus(HttpStatus.UNAUTHORIZED)
                            .build());
        }
        User user = userRepository.findByIdAndDeletedAtIsNull(userId).
                orElseThrow(() -> new RuntimeException("User not found"));
        String newAccessToken = jwtProvider.generateAccessToken(user);

        return ResponseEntity.ok(
                ResponseDto.<String>builder()
                        .message("Access token successfully updated")
                        .httpStatus(HttpStatus.OK)
                        .data(newAccessToken)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDto<Void>> logout(UUID userId) {
        refreshTokenService.deleteRefreshToken(userId);
        return ResponseEntity.ok(ResponseDto.<Void>builder()
                .httpStatus(HttpStatus.OK)
                .message("User logged out successfully")
                .build());
    }

//    @Override
//    public ResponseEntity<ResponseDto<Map<String, String>>> authenticateWithGoogle(GoogleTokenDto dto) {
//        return null;
//    }
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
//
//    @Override
//    public ResponseEntity<ResponseDto<Map<String, String>>> handleGoogleCallback(String code) {
//        logger.info("Processing Google OAuth callback with code: {}", code);
//        try {
//            GoogleTokenResponse googleTokens = googleClient.exchangeCodeForToken(code);
//            logger.debug("Received Google tokens: {}", googleTokens);
//
//            GoogleUserInfo googleUser = googleClient.getUserInfo(googleTokens.getAccessToken());
//            logger.debug("Fetched Google user info: {}", googleUser.getEmail());
//
//            User user = userRepository.findByEmail(googleUser.getEmail())
//                    .orElseGet(() -> {
//                        logger.info("Creating new user with email: {}", googleUser.getEmail());
//                        User newUser = new User();
//                        newUser.setEmail(googleUser.getEmail());
//                        newUser.setFirstName(googleUser.getGiven_name());
//                        newUser.setLastName(googleUser.getFamily_name());
//                        newUser.setPicture(googleUser.getPicture());
//                        newUser.setRoles(List.of(Role.USER));
//                        newUser.setCreatedAt(LocalDateTime.now());
//                        return userRepository.save(newUser);
//                    });
//
//            String jwt = jwtProvider.generateAccessToken(user);
//            String refreshToken = jwtProvider.generateRefreshToken(user);
//            refreshTokenService.storeRefreshToken(user.getId(), refreshToken);
//
//            Map<String, String> tokens = new HashMap<>();
//            tokens.put("accessToken", jwt);
//            tokens.put("refreshToken", refreshToken);
//            tokens.put("google_access_token", googleTokens.getAccessToken());
//
//            logger.info("Google authentication successful for user: {}", googleUser.getEmail());
//            return ResponseEntity.ok(ResponseDto.<Map<String, String>>builder()
//                    .data(tokens)
//                    .message("Google authentication successful")
//                    .httpStatus(HttpStatus.OK)
//                    .build());
//        } catch (Exception e) {
//            logger.error("Google authentication failed: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(ResponseDto.<Map<String, String>>builder()
//                            .message("Google authentication failed: " + e.getMessage())
//                            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//                            .build());
//        }
//    }
}
