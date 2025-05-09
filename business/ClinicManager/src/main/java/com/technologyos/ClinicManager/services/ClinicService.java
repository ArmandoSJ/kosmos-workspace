package com.technologyos.ClinicManager.services;

import com.technologyos.ClinicManager.dtos.request.ClinicRequest;
import com.technologyos.ClinicManager.entities.ClinicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClinicService {
   Page<ClinicEntity> findAll(Pageable pageable);

   ClinicEntity findClinicById(Long clinicId);

   ClinicEntity createClinic(ClinicRequest saveClinic);

   ClinicEntity updateClinicById(Long clinicId, ClinicRequest saveClinic);
}
