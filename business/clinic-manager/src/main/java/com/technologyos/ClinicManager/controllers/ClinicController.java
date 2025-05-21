package com.technologyos.ClinicManager.controllers;

import com.technologyos.ClinicManager.dtos.request.ClinicRequest;
import com.technologyos.ClinicManager.dtos.request.DoctorRequest;
import com.technologyos.ClinicManager.entities.ClinicEntity;
import com.technologyos.ClinicManager.entities.DoctorEntity;
import com.technologyos.ClinicManager.services.ClinicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clinics")
@AllArgsConstructor
public class ClinicController {

   private final ClinicService clinicService;

   @GetMapping
   public ResponseEntity<Page<ClinicEntity>> findAll(Pageable pageable){
      Page<ClinicEntity> clinicPage = clinicService.findAll(pageable);

      if(clinicPage.hasContent()){
         return ResponseEntity.ok(clinicPage);
      }

      return ResponseEntity.notFound().build();
   }

   @GetMapping("/{clinicId}")
   public ResponseEntity<ClinicEntity> findClinicById(@PathVariable("clinicId") Long clinicId){
      return ResponseEntity.ok(clinicService.findClinicById(clinicId));
   }

   @PostMapping
   public ResponseEntity<ClinicEntity> createClinic(@RequestBody @Valid ClinicRequest saveClinic){
      ClinicEntity clinic = clinicService.createClinic(saveClinic);
      return ResponseEntity.status(HttpStatus.CREATED).body(clinic);
   }

   @PutMapping("/{clinicId}")
   public ResponseEntity<ClinicEntity> updateClinicById(@PathVariable("clinicId") Long clinicId ,
                                                        @RequestBody @Valid ClinicRequest saveClinic){
      ClinicEntity clinic = clinicService.updateClinicById(clinicId, saveClinic);
      return ResponseEntity.ok(clinic);
   }
}
