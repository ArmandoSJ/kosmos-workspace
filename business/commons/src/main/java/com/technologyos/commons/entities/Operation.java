package com.technologyos.commons.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Operation {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long operationId;
   private String name;
   private String path;
   private String httpMethod;
   private boolean permitAll;

   @ManyToOne
   @JoinColumn(name = "module_id")
   private Module module;
}
