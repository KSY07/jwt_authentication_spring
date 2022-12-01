/**
 * @author: 김세영
 * @version: 1.0.0
 * @param: Authentication 실패시 예외처리하는 EntryPoint 작성
 */


package com.scarf.fracas.authsvr.Configuration;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ScarfAuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint, Serializable {

    private final Logger logger = LoggerFactory.getLogger(ScarfAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                            AuthenticationException authException) throws IOException, ServletException {
                                logger.error("Unauthorize error: {}", authException.getMessage());

                                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                                final Map<String,Object> body = new HashMap<>();
                                body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
                                body.put("error", "Unauthorized");
                                body.put("message", authException.getMessage());
                                body.put("path", request.getServletPath());

                                final ObjectMapper mapper = new ObjectMapper();
                                mapper.writeValue(response.getOutputStream(), body);
                            }
    
}
