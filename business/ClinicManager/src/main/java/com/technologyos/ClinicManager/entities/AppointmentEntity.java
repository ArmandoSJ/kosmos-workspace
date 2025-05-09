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
@SequenceGenerator(name = "appointments_seq", sequenceName = "appointments_sequence", allocationSize = 1)
@Table(name = "appointments")
@Getter
@Setter
@ToString
public class AppointmentEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointments_sequence")
   @Column(nullable = false)
   private Long appointmentId;

   @Column(name = "clinic_id", nullable = false)
   private Long clientId;

   @Column(name = "doctor_id", nullable = false)
   private Long doctorId;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "clinic_id", insertable = false, updatable = false)
   private ClinicEntity clinic;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "doctor_id", insertable = false, updatable = false)
   private DoctorEntity doctor;

   private LocalDateTime appointmentTime;
   private String patientName;

   @CreationTimestamp
   @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime createdAt;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      AppointmentEntity that = (AppointmentEntity) o;
      return Objects.equals(appointmentId, that.appointmentId);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(appointmentId);
   }
}
