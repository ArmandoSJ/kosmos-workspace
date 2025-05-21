package com.technologyos.commons.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtToken {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(length = 2048)
   private String token;
   private Date expiration;
   private boolean isValid;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;
}
