package com.technologyos.commons.exceptions;

public class AppointmentException extends IllegalArgumentException {
   public AppointmentException() {}

   public AppointmentException(String message) {
      super(message);
   }

   public AppointmentException(String message, Throwable cause) {
      super(message, cause);
   }
}
