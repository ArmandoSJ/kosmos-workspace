package com.technologyos.ClinicManager.repositories;

import com.technologyos.ClinicManager.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
}
