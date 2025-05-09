package com.technologyos.ClinicManager.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorRequest {
   private String name;
   private String lastNamePaternal;
   private String lastNameMaternal;
   private String specialty;
}
