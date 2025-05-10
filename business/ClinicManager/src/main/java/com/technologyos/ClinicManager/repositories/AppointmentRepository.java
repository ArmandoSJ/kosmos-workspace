package com.technologyos.ClinicManager.repositories;

import com.technologyos.ClinicManager.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

   @Query("""
    SELECT a FROM AppointmentEntity a
    WHERE a.appointmentTime BETWEEN :startOfDay AND :endOfDay
   """)
   List<AppointmentEntity> findByDate(@Param("startOfDay") LocalDateTime startOfDay,
                                      @Param("endOfDay") LocalDateTime endOfDay);

   @Query("SELECT a FROM AppointmentEntity a WHERE a.clinic.roomNumber = :roomNumber")
   List<AppointmentEntity> findByRoomNumber(@Param("roomNumber") String roomNumber);


   @Query("SELECT a FROM AppointmentEntity a WHERE a.doctor.name = :doctorName")
   List<AppointmentEntity> findByDoctorName(@Param("doctorName") String doctorName);

   @Query(value = """
    SELECT EXISTS (
        SELECT 1 FROM appointments
        WHERE clinic_id = :clinicId
        AND appointment_time = :appointmentTime
    )
   """, nativeQuery = true)
   boolean existsByClinicAndAppointmentTime(
      @Param("clinicId") Long clinicId,
      @Param("appointmentTime") LocalDateTime appointmentTime
   );

   @Query(value = """
    SELECT EXISTS (
        SELECT 1 FROM appointments
        WHERE doctor_id = :doctorId
        AND appointment_time = :appointmentTime
    )
   """, nativeQuery = true)
   boolean existsByDoctorAndAppointmentTime(
      @Param("doctorId") Long doctorId,
      @Param("appointmentTime") LocalDateTime appointmentTime
   );

   @Query(value = """
    SELECT EXISTS (
        SELECT 1 FROM appointments
        WHERE patient_name = :patientName
        AND DATE(appointment_time) = DATE(:appointmentTime)
        AND ABS(EXTRACT(EPOCH FROM (appointment_time - :appointmentTime))) < 7200
    )
   """, nativeQuery = true)
   boolean existsPatientConflictSameDay(
      @Param("patientName") String patientName,
      @Param("appointmentTime") LocalDateTime appointmentTime
   );

   @Query(value = """
    SELECT COUNT(*) FROM appointments
    WHERE doctor_id = :doctorId
    AND appointment_time BETWEEN :startOfDay AND :endOfDay
   """, nativeQuery = true)
   int countAppointmentsForDoctorInDay(
      @Param("doctorId") Long doctorId,
      @Param("startOfDay") LocalDateTime startOfDay,
      @Param("endOfDay") LocalDateTime endOfDay
   );
}
