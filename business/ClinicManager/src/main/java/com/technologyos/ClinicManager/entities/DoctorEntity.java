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
@SequenceGenerator(name = "doctors_seq", sequenceName = "doctors_sequence", allocationSize = 1)
@Table(name = "doctors")
@Getter
@Setter
@ToString
public class DoctorEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctors_seq")
   @Column(name = "doctor_id", nullable = false)
   private Long doctorId;
   private String name;
   private String lastNamePaternal;
   private String lastNameMaternal;
   private String specialty;

   @CreationTimestamp
   @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime createdAt;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      DoctorEntity that = (DoctorEntity) o;
      return Objects.equals(doctorId, that.doctorId);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(doctorId);
   }
}
