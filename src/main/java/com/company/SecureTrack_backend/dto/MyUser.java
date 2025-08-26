package com.company.SecureTrack_backend.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyUser implements UserDetails, Serializable {
    private UUID id;
    private String username;
    private String password;
    private String accesses;
    private String status;
    private String roles;
    private String token;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Agar sizda expiration haqida field bo'lsa, shunga qarab yozing
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Agar bloklangan bo'lsa, false qaytaradi
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Agar parolning muddati tugagan bo'lsa, false qaytaradi
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Agar foydalanuvchi aktiv bo'lmasa, false qaytaradi
        return true;
    }
}
