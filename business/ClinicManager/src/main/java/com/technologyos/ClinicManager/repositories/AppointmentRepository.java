package com.technologyos.ClinicManager.repositories;

import com.technologyos.ClinicManager.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

   // 2. Buscar por número de consultorio
   @Query("SELECT a FROM AppointmentEntity a WHERE a.clinic.roomNumber = :roomNumber")
   List<AppointmentEntity> findByRoomNumber(@Param("roomNumber") String roomNumber);

   // 3. Buscar por nombre del doctor
   @Query("SELECT a FROM AppointmentEntity a WHERE a.doctor.name = :doctorName")
   List<AppointmentEntity> findByDoctorName(@Param("doctorName") String doctorName);
}
