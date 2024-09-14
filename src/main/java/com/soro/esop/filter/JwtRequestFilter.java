package com.soro.esop.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.soro.esop.service.CustomUserDetailService;
import com.soro.esop.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * packageName : com.soro.esop.filter
 * fileName    : JwtRequestFilter
 * Description : JWT 요청 필터
 *              - JWT 토큰을 요청에서 추출하고, 토큰이 유효한지 확인하여 사용자 인증을 처리하는 필터
 *              - JWT 토큰이 만료되었을 경우, 리프레시 토큰을 사용하여 새로운 JWT 토큰을 발급
 *              - JWT 토큰이 유효하지 않을 경우, 사용자 인증을 제거
 *              - JWT 토큰이 유효할 경우, 사용자 인증을 설정
 * ===========================================================
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter{

    private final CustomUserDetailService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        Map<String, String> tokens = extractJwtFromRequest(request);
        String jwt = tokens.get("jwt");
        String refreshToken = tokens.get("refreshToken");

        String username = null;

        if (jwt != null) {
            try {
                username = jwtUtil.extractUsername(jwt);
                if (jwtUtil.isTokenExpired(jwt)) {
                    log.info("JWT token has expired, attempting to use refresh token");
                    if (refreshToken != null && !jwtUtil.isTokenExpired(refreshToken)) {
                        username = jwtUtil.extractUsername(refreshToken);
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        String newJwt = jwtUtil.generateToken(userDetails);
                        // Set the new JWT in a cookie or header
                        response.addCookie(new Cookie("jwt", newJwt));
                    } else {
                        log.info("Refresh token is invalid or expired");
                        clearAuthentication(); // Clear the authentication
                        username = null;
                    }
                }
            } catch (Exception e) {
                log.error("Error processing JWT token", e);
                clearAuthentication();
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    clearAuthentication();
                }
            } catch (Exception e) {
                log.error("Error setting user authentication", e);
                clearAuthentication();
            }
        }

        filterChain.doFilter(request, response);
    }

    private Map<String, String> extractJwtFromRequest(HttpServletRequest request) {

        // Check cookies first
        Cookie[] cookies = request.getCookies();
        Map<String, String> tokens = new HashMap<>();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    tokens.put("jwt", cookie.getValue());
                } else if ("refreshToken".equals(cookie.getName())) {
                    tokens.put("refreshToken", cookie.getValue());
                }
            }
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            tokens.put("jwt", authHeader.substring(7));
        }

        return tokens;
    }

    private void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }
}
