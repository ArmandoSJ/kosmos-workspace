package com.technologyos.ClinicManager.services;

import com.technologyos.ClinicManager.dtos.request.DoctorRequest;
import com.technologyos.ClinicManager.entities.DoctorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {
   Page<DoctorEntity> findAll(Pageable pageable);

   DoctorEntity findDoctorById(Long doctorId);

   DoctorEntity createDoctor(DoctorRequest saveDoctor);

   DoctorEntity updateDoctorById(Long doctorId, DoctorRequest saveDoctor);
}
