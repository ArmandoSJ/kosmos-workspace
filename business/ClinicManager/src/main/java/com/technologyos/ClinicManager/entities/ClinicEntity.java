package com.technologyos.ClinicManager.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "clinics_seq", sequenceName = "clinics_sequence", allocationSize = 1)
@Table(name = "clinics")
@Getter
@Setter
@ToString
public class ClinicEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clinics_sequence")
   @Column(nullable = false)
   private Long clinicId;
   private String roomNumber;
   private int floor;

   @CreationTimestamp
   @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime createdAt;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ClinicEntity that = (ClinicEntity) o;
      return Objects.equals(clinicId, that.clinicId);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(clinicId);
   }
}
