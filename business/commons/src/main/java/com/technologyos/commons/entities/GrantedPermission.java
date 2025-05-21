package com.technologyos.commons.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrantedPermission {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long grantedPermissionId;

   @ManyToOne
   @JoinColumn(name = "role_id")
   private Role role;

   @ManyToOne
   @JoinColumn(name = "operation_id")
   private Operation operation;
}
