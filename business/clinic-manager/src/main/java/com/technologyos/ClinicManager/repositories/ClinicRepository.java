package com.technologyos.ClinicManager.repositories;

import com.technologyos.ClinicManager.entities.ClinicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<ClinicEntity, Long> {
}
