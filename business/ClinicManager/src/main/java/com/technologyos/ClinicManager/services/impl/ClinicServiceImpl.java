package com.technologyos.ClinicManager.services.impl;

import com.technologyos.ClinicManager.dtos.request.ClinicRequest;
import com.technologyos.ClinicManager.entities.ClinicEntity;
import com.technologyos.ClinicManager.exceptions.ObjectNotFoundException;
import com.technologyos.ClinicManager.repositories.ClinicRepository;
import com.technologyos.ClinicManager.services.ClinicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClinicServiceImpl implements ClinicService {
   private final ClinicRepository clinicRepository;

   @Override
   public Page<ClinicEntity> findAll(Pageable pageable) {
      return clinicRepository.findAll(pageable);
   }

   @Override
   public ClinicEntity findClinicById(Long clinicId) {
      return clinicRepository.findById(clinicId)
         .orElseThrow( () -> new ObjectNotFoundException("Clinic not found with id " + clinicId));
   }

   @Override
   public ClinicEntity createClinic(ClinicRequest saveClinic) {
      ClinicEntity clinicEntity = new ClinicEntity();
      clinicEntity.setFloor(saveClinic.getFloor());
      clinicEntity.setRoomNumber(saveClinic.getRoomNumber());
      return clinicRepository.save(clinicEntity);
   }

   @Override
   public ClinicEntity updateClinicById(Long clinicId, ClinicRequest saveClinic) {
      ClinicEntity clinicEntity = this.findClinicById(clinicId);
      clinicEntity.setFloor(saveClinic.getFloor());
      clinicEntity.setRoomNumber(saveClinic.getRoomNumber());
      return clinicRepository.save(clinicEntity);
   }
}
