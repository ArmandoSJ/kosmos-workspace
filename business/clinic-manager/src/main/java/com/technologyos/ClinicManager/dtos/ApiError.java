package com.technologyos.ClinicManager.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError implements Serializable {
   private String backendMessage;
   private String message;
   private String url;
   private String method;
   @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
   private LocalDateTime timestamp;
}
