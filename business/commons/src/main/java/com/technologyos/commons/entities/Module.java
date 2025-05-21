package com.technologyos.commons.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Module {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long moduleId;
   private String name;
   private String basePath;
}
