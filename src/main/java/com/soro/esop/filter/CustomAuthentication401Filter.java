package com.soro.esop.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * packageName : com.soro.esop.filter
 * fileName    : CustomAuthentication401Filter
 * Description : 사용자 인증 필터
 *             - 사용자가 인증되지 않은 경우, 401 상태 코드를 반환하거나 로그인 페이지로 리다이렉트
 *             - 사용자가 인증되지 않은 경우, API 요청에 대해 401 상태 코드를 반환
 *             - 사용자가 인증되지 않은 경우, 페이지 요청에 대해 로그인 페이지로 리다이렉트
 */
@Slf4j
@Component
public class CustomAuthentication401Filter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException 
    {
        // Get the request path
        String path = request.getRequestURI();

        // Check if the user is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // List of paths that require authentication
        List<String> pathList = Arrays.asList("/board/list", "/dx/entryList", "/dx/userList");

        Boolean isApiRequest = isApiRequest(request);

        // If the user is not authenticated and the request path is "/board/list"
        if(pathList.contains(path) &&
            (authentication == null || authentication instanceof AnonymousAuthenticationToken))
        {
            if(isApiRequest) {
                log.info("Unauthorized access to the API");
                // Set the response status to 401 (Unauthorized)
                response.setStatus(HttpStatus.UNAUTHORIZED.value());  // 401
                // Write "Unauthorized" to the response
                response.getWriter().write("Unauthorized");
                return;
            } else {
                log.info("Redirecting to the login page");
                // Redirect the user to the login page
                response.sendRedirect("/account/login");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /*
     * Check if the request is an API request
     */
    private boolean isApiRequest(HttpServletRequest request) {
        // Get the "Accept" header from the request
        String accept = request.getHeader("Accept");

        // Check if the "Accept" header contains "application/json"
        return accept != null && accept.contains("application/json");
    }
    
}
