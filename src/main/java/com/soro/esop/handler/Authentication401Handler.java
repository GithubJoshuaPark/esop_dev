package com.soro.esop.handler;

import static java.time.LocalTime.now;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soro.esop.domain.HttpResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author : Joshua Park
 * @email : soromiso@gmail.com
 * @create date : 2024-08-20 21:26:33
 * @modify date : 2024-08-20 21:26:33
 * @desc [description]
 *  This HTTP status code, with a numeric value of '401',
 * indicates that the client making the request is not authenticated.
 * In other words, the client hasn't provided valid credentials
 * (such as a username and password or a token) to access the requested resource.
 */

/**
 * packageName : com.soro.esop.handler
 * fileName    : Authentication401Handler
 * Description : 401 Unauthorized Handler
 *              - 인증되지 않은 사용자가 페이지에 액세스하려고 할 때 호출되는 핸들러
 *              - 사용자가 인증되지 않은 경우 401 상태 코드와 함께 사용자에게 메시지를 반환
 * ===========================================================
 */
@Component
public class Authentication401Handler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, 
                         HttpServletResponse response, 
                         AuthenticationException authException) throws IOException, ServletException {
        //response.sendRedirect("/error/401");

        HttpResponse httpResponse = HttpResponse.builder()
                .timeStamp(now().toString())
                .status(HttpStatus.UNAUTHORIZED)
                .statusCode(HttpStatus.UNAUTHORIZED.value()) // 401
                .reason("You are not authorized user who can access this page")
                .developerMessage(authException.getMessage())
                .build();

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401

        OutputStream outputStream = response.getOutputStream(); // get the output stream from the response object
        ObjectMapper mapper       = new ObjectMapper();
        mapper.writeValue(outputStream, httpResponse); // serialize the HttpResponse object to JSON format into the output stream
        outputStream.flush(); // flush the output stream, which sends the response data to the client
    }
    
}
