package com.company.SecureTrack_backend.config;

import com.company.SecureTrack_backend.config.jwt.JwtProvider;
import com.company.SecureTrack_backend.model.User;
import com.company.SecureTrack_backend.model.enums.Role;
import com.company.SecureTrack_backend.repository.UserRepository;
import com.company.SecureTrack_backend.service.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String firstname = oAuth2User.getAttribute("given_name");
        String lastname = oAuth2User.getAttribute("family_name");
        String picture = oAuth2User.getAttribute("picture");

        // 1. User bazada bormi? Agar yo‘q bo‘lsa create
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setFirstName(firstname);
                    newUser.setLastName(lastname);
                    newUser.setPicture(picture);
                    newUser.setRoles(List.of(Role.USER));
                    newUser.setCreatedAt(LocalDateTime.now());
                    return userRepository.save(newUser);
                });

        // 2. JWT yaratish
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshTokenService.storeRefreshToken(user.getId(), refreshToken);
        // 3. JSON response qaytarish
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseData = Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken,
                "user", Map.of(
                        "id", user.getId(),
                        "email", user.getEmail(),
                        "firstname", user.getFirstName(),
                        "lastname", user.getLastName(),
                        "picture", user.getPicture()
                )
        );
        response.getWriter().write(mapper.writeValueAsString(responseData));
    }

}
