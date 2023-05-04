package com.tawfeek.quizApi.Utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    public static String getCurrentUserEmail() {
        var principal = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();
        var email = principal.getUsername();
        return email;
    }
}
