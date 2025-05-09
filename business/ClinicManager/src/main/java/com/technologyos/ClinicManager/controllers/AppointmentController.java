package com.technologyos.ClinicManager.controllers;

import com.technologyos.ClinicManager.dtos.request.AppointmentRequest;
import com.technologyos.ClinicManager.dtos.request.ClinicRequest;
import com.technologyos.ClinicManager.entities.AppointmentEntity;
import com.technologyos.ClinicManager.entities.ClinicEntity;
import com.technologyos.ClinicManager.services.AppointmentService;
import com.technologyos.ClinicManager.services.ClinicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
public class AppointmentController {

   private final AppointmentService appointmentService;

   @GetMapping
   public ResponseEntity<Page<AppointmentEntity>> findAll(Pageable pageable){
      Page<AppointmentEntity> appointmentPage = appointmentService.findAll(pageable);

      if(appointmentPage.hasContent()){
         return ResponseEntity.ok(appointmentPage);
      }

      return ResponseEntity.notFound().build();
   }

   @GetMapping("/{appointmentId}")
   public ResponseEntity<AppointmentEntity> findAppointmentById(@PathVariable("appointmentId") Long appointmentId){
      return ResponseEntity.ok(appointmentService.findAppointmentById(appointmentId));
   }

   @GetMapping("/room/{roomNumber}")
   public ResponseEntity<List<AppointmentEntity>> findAppointmentByRoomNumber(@PathVariable("roomNumber") String roomNumber){
      return ResponseEntity.ok(appointmentService.findAppointmentByRoomNumber(roomNumber));
   }

   @GetMapping("/doctor/{doctorName}")
   public ResponseEntity<List<AppointmentEntity>> findAppointmentByDoctorName(@PathVariable("doctorName") String doctorName){
      return ResponseEntity.ok(appointmentService.findAppointmentByDoctorName(doctorName));
   }

   @PostMapping
   public ResponseEntity<AppointmentEntity> createAppointment(@RequestBody @Valid AppointmentRequest saveAppointment){
      AppointmentEntity appointment = appointmentService.createAppointment(saveAppointment);
      return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
   }

   @PutMapping("/{appointmentId}")
   public ResponseEntity<AppointmentEntity> updateAppointmentById(@PathVariable("appointmentId") Long appointmentId ,
                                                                  @RequestBody @Valid AppointmentRequest saveAppointment){
      AppointmentEntity appointment = appointmentService.updateAppointmentById(appointmentId, saveAppointment);
      return ResponseEntity.ok(appointment);
   }
}
