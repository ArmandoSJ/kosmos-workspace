package com.technologyos.commons.dtos.permissions;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRequest implements Serializable {
   @NotBlank
   private String role;
   @NotBlank
   private String operation;
}
