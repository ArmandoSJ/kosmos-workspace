package com.technologyos.ClinicManager.controllers;

import com.technologyos.ClinicManager.dtos.request.AppointmentRequest;
import com.technologyos.ClinicManager.entities.AppointmentEntity;
import com.technologyos.ClinicManager.services.AppointmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

   @GetMapping("/date/{date}")
   public ResponseEntity<List<AppointmentEntity>> findAppointmentByDate(@PathVariable("date") LocalDateTime date){
      return ResponseEntity.ok(appointmentService.findAppointmentByDate(date));
   }

   @GetMapping("/by-doctor")
   public ResponseEntity<Long> findAppointmentByDate(@RequestParam String doctorName,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date){
      return ResponseEntity.ok(appointmentService.findAppointmentsByDoctorNameAndDate(doctorName, date));
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
   public ResponseEntity<String> updateAppointment(@PathVariable("appointmentId") Long appointmentId ,
                                                   @RequestBody @Valid AppointmentRequest saveAppointment){
      try {
         appointmentService.cancelAppointment(appointmentId);
         return ResponseEntity.ok("Appointment cancelled successfully.");
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to cancel appointment.");
      }
   }

   @DeleteMapping("/{appointmentId}")
   public ResponseEntity<String> cancelAppointment(@PathVariable("appointmentId") Long appointmentId,
                                                   @RequestBody AppointmentRequest newData){
      try {
         appointmentService.updateAppointment(appointmentId, newData);
         return ResponseEntity.ok("Appointment updated successfully.");
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update appointment.");
      }
   }

}
