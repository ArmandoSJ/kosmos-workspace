package com.technologyos.commons.exceptions;

import com.technologyos.commons.dtos.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(Exception.class)
   public ResponseEntity<?> handlerGenericException(HttpServletRequest request, Exception exception){
      ApiError apiError = ApiError.builder()
         .backendMessage(exception.getLocalizedMessage())
         .url(request.getRequestURL().toString())
         .method(request.getMethod())
         .message("Error interno en el servidor, vuelva a intentarlo")
         .timestamp(LocalDateTime.now())
         .build();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<?> handlerMethodArgumentNotValidException(HttpServletRequest request,
                                                                   MethodArgumentNotValidException exception){
      ApiError apiError = ApiError.builder()
              .backendMessage(exception.getLocalizedMessage())
              .url(request.getRequestURL().toString())
              .method(request.getMethod())
              .message("Error en la petición enviada")
              .timestamp(LocalDateTime.now())
              .build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
   }

   @ExceptionHandler(AppointmentException.class)
   public ResponseEntity<?> handlerAppointmentException(HttpServletRequest request, AppointmentException exception){
      ApiError apiError = ApiError.builder()
              .backendMessage(exception.getLocalizedMessage())
              .url(request.getRequestURL().toString())
              .method(request.getMethod())
              .message("Error en la petición enviada")
              .timestamp(LocalDateTime.now())
              .build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
   }

   @ExceptionHandler(AccessDeniedException.class)
   public ResponseEntity<?> handlerAccessDeniedException(HttpServletRequest request, AccessDeniedException exception){
      ApiError apiError = ApiError.builder()
         .backendMessage(exception.getLocalizedMessage())
         .url(request.getRequestURL().toString())
         .method(request.getMethod())
         .message("Acceso denegado. No tienes los permisos necesarios para acceder a esta función. " +
            "Por favor, contacta al administrador si crees que esto es un error.")
         .timestamp(LocalDateTime.now())
         .build();
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
   }


}
