package com.technologyos.commons.dtos.signup;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisteredUser implements Serializable {
   private Long userId;
   private String username;
   private String name;
   private String role;
   private String jwt;
}
