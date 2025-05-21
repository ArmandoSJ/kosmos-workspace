package com.technologyos.commons.configs.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.technologyos.commons.dtos.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Custom access denied handler for unauthorized access attempts.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

   private static final String ACCESS_DENIED_MESSAGE = "Access denied. You do not have permission to access this resource. "
      + "Please contact the administrator if you believe this is a mistake.";

   private final ObjectMapper objectMapper;

   public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
      this.objectMapper = objectMapper.copy().registerModule(new JavaTimeModule());
   }

   @Override
   public void handle(HttpServletRequest request, HttpServletResponse response,
                      AccessDeniedException accessDeniedException) throws IOException {
      ApiError apiError = ApiError.builder()
         .backendMessage(accessDeniedException.getLocalizedMessage())
         .url(request.getRequestURL().toString())
         .method(request.getMethod())
         .message(ACCESS_DENIED_MESSAGE)
         .timestamp(LocalDateTime.now())
         .build();

      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.getWriter().write(objectMapper.writeValueAsString(apiError));
   }
}
