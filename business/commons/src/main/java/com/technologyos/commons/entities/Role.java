package com.technologyos.commons.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Entity
@Data
public class Role {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long roleId;
   private String name;

   @Getter
   @JsonIgnore
   @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
   private List<GrantedPermission> permissions;
}
