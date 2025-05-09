package com.technologyos.ClinicManager.services.impl;

import com.technologyos.ClinicManager.dtos.request.DoctorRequest;
import com.technologyos.ClinicManager.entities.DoctorEntity;
import com.technologyos.ClinicManager.exceptions.ObjectNotFoundException;
import com.technologyos.ClinicManager.repositories.DoctorRepository;
import com.technologyos.ClinicManager.services.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {

   private final DoctorRepository doctorRepository;

   @Override
   public Page<DoctorEntity> findAll(Pageable pageable) {
      return doctorRepository.findAll(pageable);
   }

   @Override
   public DoctorEntity findDoctorById(Long doctorId) {
      return doctorRepository.findById(doctorId)
         .orElseThrow( () -> new ObjectNotFoundException("Doctor not found with id " + doctorId));
   }

   @Override
   public DoctorEntity createDoctor(DoctorRequest saveDoctor) {
      DoctorEntity doctorEntity = new DoctorEntity();
      doctorEntity.setName(saveDoctor.getName());
      doctorEntity.setLastNameMaternal(saveDoctor.getLastNameMaternal());
      doctorEntity.setLastNamePaternal(saveDoctor.getLastNamePaternal());
      doctorEntity.setSpecialty(saveDoctor.getSpecialty());
      return doctorRepository.save(doctorEntity);
   }

   @Override
   public DoctorEntity updateDoctorById(Long doctorId, DoctorRequest saveDoctor) {
      DoctorEntity doctorFromDb = this.findDoctorById(doctorId);
      doctorFromDb.setName(saveDoctor.getName());
      doctorFromDb.setLastNameMaternal(saveDoctor.getLastNameMaternal());
      doctorFromDb.setLastNamePaternal(saveDoctor.getLastNamePaternal());
      doctorFromDb.setSpecialty(saveDoctor.getSpecialty());
      return doctorRepository.save(doctorFromDb);
   }
}
