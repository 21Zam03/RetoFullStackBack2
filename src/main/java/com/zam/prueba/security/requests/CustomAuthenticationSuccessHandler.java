package com.zam.prueba.security.requests;

import com.zam.prueba.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    public CustomAuthenticationSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof OidcUser) {
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            String email = oidcUser.getAttribute("email");

            Authentication customAuth = new UsernamePasswordAuthenticationToken(
                    email,
                    authentication.getCredentials(),
                    authentication.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(customAuth);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String accessToken = jwtUtil.createToken(auth.getName(), auth.getAuthorities());

        Cookie cookie = new Cookie("auth_token", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(1800);
        response.addCookie(cookie);
        response.sendRedirect("http://localhost:4200/dashboard");

    }
}
