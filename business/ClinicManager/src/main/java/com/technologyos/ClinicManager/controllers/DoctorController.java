package com.technologyos.ClinicManager.controllers;

import com.technologyos.ClinicManager.dtos.request.DoctorRequest;
import com.technologyos.ClinicManager.entities.DoctorEntity;
import com.technologyos.ClinicManager.services.DoctorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
@AllArgsConstructor
public class DoctorController {

   private final DoctorService doctorService;

   @GetMapping
   public ResponseEntity<Page<DoctorEntity>> findAll(Pageable pageable){
      Page<DoctorEntity> doctorsPage = doctorService.findAll(pageable);

      if(doctorsPage.hasContent()){
         return ResponseEntity.ok(doctorsPage);
      }

      return ResponseEntity.notFound().build();
   }

   @GetMapping("/{doctorId}")
   public ResponseEntity<DoctorEntity> findDoctorById(@PathVariable("doctorId") Long doctorId){
      return ResponseEntity.ok(doctorService.findDoctorById(doctorId));
   }

   @PostMapping
   public ResponseEntity<DoctorEntity> createDoctor(@RequestBody @Valid DoctorRequest saveDoctor){
      DoctorEntity doctor = doctorService.createDoctor(saveDoctor);
      return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
   }

   @PutMapping("/{doctorId}")
   public ResponseEntity<DoctorEntity> updateDoctorById(@PathVariable("doctorId") Long doctorId ,
                                                         @RequestBody @Valid DoctorRequest saveDoctor){
      DoctorEntity doctor = doctorService.updateDoctorById(doctorId, saveDoctor);
      return ResponseEntity.ok(doctor);
   }
}
