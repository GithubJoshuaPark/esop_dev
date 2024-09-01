package com.soro.esop.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.soro.esop.utils.JwtUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final long expiration;
    private final long refreshExpiration;

    public JwtAuthenticationSuccessHandler(JwtUtil jwtUtil, long expiration, long refreshExpiration) {
        this.jwtUtil = jwtUtil;
        this.expiration = expiration;
        this.refreshExpiration = refreshExpiration;
    }

    /**
     * 인증 성공 시 JWT 토큰을 생성하고 쿠키에 저장한다.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                        HttpServletResponse response, 
                                        Authentication authentication) throws IOException, ServletException 
    {
        log.info("Authentication Success");
        log.info("expiration: {}", expiration);
        log.info("refreshExpiration: {}", refreshExpiration);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);               // 30 mins
        String refreshToken = jwtUtil.generateRefreshToken(userDetails); // 5 days


        // Set JWT and RefreshToken as a cookie
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(request.isSecure()) // true for HTTPS
                .path("/")
                .maxAge(expiration * 1000) //  30 mins
                .sameSite("Strict")  // or "Lax" depending on your requirements
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(request.isSecure()) // true for HTTPS
                //.path("/api/auth/refresh") // restrict to refresh endpoint
                .path("/api") // restrict to /api endpoint
                .maxAge(refreshExpiration * 1000) // 5 days
                //.sameSite("Strict")  // or "Lax" depending on your requirements
                .sameSite("Lax")  // or "Lax" depending on your requirements
                .build();

        response.addHeader("Set-Cookie", jwtCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());

        response.sendRedirect("/board/list");
    }

    
}
