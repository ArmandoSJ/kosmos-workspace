package com.technologyos.ClinicManager.repositories;

import com.technologyos.ClinicManager.entities.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
}
