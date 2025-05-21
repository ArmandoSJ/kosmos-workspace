package com.technologyos.commons.dtos.permissions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionResponse implements Serializable {
   private long permissionId;
   private String operation;
   private String httpMethod;
   private String module;
   private String role;
}
