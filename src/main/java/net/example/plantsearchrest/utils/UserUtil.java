package net.example.plantsearchrest.utils;

import net.example.plantsearchrest.security.jwt.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    /**
     * Retrieves the currently authenticated user.
     *
     * @return The authenticated user as a JwtUser object.
     */
    public static JwtUser getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser user = (JwtUser) authentication.getPrincipal();
        return user;
    }
}
