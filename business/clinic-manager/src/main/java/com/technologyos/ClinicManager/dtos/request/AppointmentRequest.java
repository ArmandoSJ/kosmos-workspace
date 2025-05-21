package com.technologyos.ClinicManager.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequest {
   @JsonProperty("clinic_id")
   @NotNull
   private Long clinicId;

   @JsonProperty("doctor_id")
   @NotNull
   private Long doctorId;

   @NotNull
   private LocalDateTime appointmentTime;

   @NotBlank
   private String patientName;
}
