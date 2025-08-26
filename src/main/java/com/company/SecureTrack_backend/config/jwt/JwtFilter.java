package com.company.SecureTrack_backend.config.jwt;

import com.company.SecureTrack_backend.dto.MyUser;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION = "Authorization";

    private static final String BEARER = "Bearer ";

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(AUTHORIZATION);
        if (header == null || !header.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(BEARER.length());

        if (!jwtProvider.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims = jwtProvider.parse(token);

        Object user = claims.get("user");

        String userStr = user.toString();

        userStr = userStr.substring(1, userStr.length() - 1);
        String[] pairs = userStr.split(", ");

        Map<String, String> userMap = new HashMap<>();
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            userMap.put(keyValue[0], keyValue.length > 1 ? keyValue[1] : "");
        }

        MyUser myUser = new MyUser();

        myUser.setToken(header);

        for (Map.Entry<String, String> entry : userMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals("username")) {
                myUser.setUsername(value);
            }
            if (key.equals("password")) {
                myUser.setPassword(value);
            }
            if (key.equals("id")) {
                myUser.setId(UUID.fromString(value));
            }
            if (key.equals("status")) {
                myUser.setStatus(value);
            }
            if (key.equals("accesses")) {
                myUser.setAccesses(value);
            }
            if (key.equals("roles")) {
                myUser.setRoles(value.toUpperCase());
            }
        }

        List<String> roles = Arrays.asList(myUser.getRoles().split(","));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        myUser,
                        null,
                        roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).toList()
                )
        );

        filterChain.doFilter(request, response);
    }
}
