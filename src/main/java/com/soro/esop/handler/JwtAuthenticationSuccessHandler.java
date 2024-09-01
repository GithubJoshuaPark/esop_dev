package com.soro.esop.handler;

import java.io.IOException;

import com.soro.esop.entity.Token;
import com.soro.esop.repository.TokenRepository;
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
    private final TokenRepository tokenRepository;

    public JwtAuthenticationSuccessHandler(JwtUtil jwtUtil, long expiration, long refreshExpiration, TokenRepository tokenRepository) {
        this.jwtUtil = jwtUtil;
        this.expiration = expiration;
        this.refreshExpiration = refreshExpiration;
        this.tokenRepository = tokenRepository;
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
        String accessToken = jwtUtil.generateToken(userDetails);               // 30 mins
        String refreshToken = jwtUtil.generateRefreshToken(userDetails); // 5 days

        // Save tokens to database
        tokenRepository.save(new Token(null, accessToken, Token.TokenType.ACCESS, false, false, userDetails.getUsername(), null));
        tokenRepository.save(new Token(null, refreshToken, Token.TokenType.REFRESH, false, false, userDetails.getUsername(), null));


        // Set JWT and RefreshToken as a cookie
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", accessToken)
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
