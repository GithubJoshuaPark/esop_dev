package com.soro.esop.handler;

import static java.time.LocalTime.now;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soro.esop.domain.HttpResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author : Joshua Park
 * @email : soromiso@gmail.com
 * @create date : 2024-08-20 21:21:11
 * @modify date : 2024-08-20 21:21:11
 * @desc [description]
 * This HTTP status code, with a numeric value of '403',
 * indicates that the client is authenticated
 * but does not have permission to access the requested resource.
 * In other words, the client has provided valid authentication credentials,
 * but those credentials don't grant them the necessary access rights.
 */

@Component
public class AccessDenied403Handler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, 
                       HttpServletResponse response, 
                       AccessDeniedException accessDeniedException) throws IOException, ServletException 
    {
        //response.sendRedirect("/error/403");

        HttpResponse httpResponse = HttpResponse.builder()
                .timeStamp(now().toString())
                .status(HttpStatus.FORBIDDEN)
                .statusCode(HttpStatus.FORBIDDEN.value()) // 403
                .reason("You don't have enough permission to access this page")
                .developerMessage(accessDeniedException.getMessage())
                .build();

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value()); // 403

        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper       = new ObjectMapper(); // create a new object mapper to serialize the HttpResponse object to JSON format.
        mapper.writeValue(outputStream, httpResponse); // write the object(JSON data) to the output stream
        outputStream.flush(); // flush the output stream
    }
    
}
