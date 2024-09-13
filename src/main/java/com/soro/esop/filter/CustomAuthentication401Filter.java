package com.soro.esop.filter;

import java.io.IOException;

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

        // If the user is not authenticated and the request path is "/board/list"
        if("/board/list".equals(path) && 
            (authentication == null || authentication instanceof AnonymousAuthenticationToken))
        {
            if(isApiRequest(request)) {
                log.info("Unauthorized access to the API");
                // Set the response status to 401 (Unauthorized)
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
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

        // if (response.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
        //     response.setStatus(HttpStatus.OK.value());
        //     response.getWriter().write("Unauthorized");
        //     response.getWriter().flush();
        //     response.getWriter().close();
        // } else {
        //     filterChain.doFilter(request, response);
        // }

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
