package com.technologyos.commons.dtos.signup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRequest implements Serializable {
   @Size(min = 10)
   @NotBlank
   private String name;

   @NotBlank
   private String username;

   @Size(min = 9)
   @NotBlank
   private String password;

   @Size(min = 9)
   @NotBlank
   private String repeatedPassword;
}
