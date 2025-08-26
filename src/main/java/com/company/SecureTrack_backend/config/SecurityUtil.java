package com.company.SecureTrack_backend.config;


import com.company.SecureTrack_backend.dto.MyUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    public MyUser getCurrentUserDetails() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof MyUser) {
            MyUser myUser = (MyUser) authentication.getPrincipal();
            return myUser;
        }

        return null;
    }
}
