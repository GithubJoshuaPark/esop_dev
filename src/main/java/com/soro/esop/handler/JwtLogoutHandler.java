package com.soro.esop.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request,
                      HttpServletResponse response,
                      Authentication authentication)
    {
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setPath("/");           // cookie is visible in all the website
        jwtCookie.setMaxAge(0);        // delete cookie
        jwtCookie.setHttpOnly(true); // prevent XSS attacks
        response.addCookie(jwtCookie);
    }

}
