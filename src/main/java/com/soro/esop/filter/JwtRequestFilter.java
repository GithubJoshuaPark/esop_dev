package com.soro.esop.filter;

import java.io.IOException;

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
        
        final Cookie[] cookies = request.getCookies();
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt      = null;

        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("jwt")) {
                    jwt = cookie.getValue();
                    username = jwtUtil.extractUsername(jwt);
                    break;
                }
            }
        }

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtUtil.validateToken(jwt, userDetails)) {
                // UsernamePasswordAuthenticationToken: A built-in object, 
                // used by spring to represent the current authenticated / being authenticated user.
                UsernamePasswordAuthenticationToken userPwdAuthToken 
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                userPwdAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // After setting the Authentication in the context, 
                // specify that the current user is authenticated.
                SecurityContextHolder.getContext().setAuthentication(userPwdAuthToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
