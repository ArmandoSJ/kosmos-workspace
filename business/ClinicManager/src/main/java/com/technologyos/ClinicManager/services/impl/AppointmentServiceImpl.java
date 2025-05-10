package com.technologyos.ClinicManager.services.impl;

import com.technologyos.ClinicManager.dtos.enums.AppointmentStatus;
import com.technologyos.ClinicManager.dtos.request.AppointmentRequest;
import com.technologyos.ClinicManager.entities.AppointmentEntity;
import com.technologyos.ClinicManager.entities.ClinicEntity;
import com.technologyos.ClinicManager.entities.DoctorEntity;
import com.technologyos.ClinicManager.exceptions.AppointmentException;
import com.technologyos.ClinicManager.exceptions.ObjectNotFoundException;
import com.technologyos.ClinicManager.repositories.AppointmentRepository;
import com.technologyos.ClinicManager.services.AppointmentService;
import com.technologyos.ClinicManager.services.ClinicService;
import com.technologyos.ClinicManager.services.DoctorService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {
   private final AppointmentRepository appointmentRepository;
   private final ClinicService clinicService;
   private final DoctorService doctorService;

   @Override
   public Page<AppointmentEntity> findAll(Pageable pageable) {
      return appointmentRepository.findAll(pageable);
   }

   @Override
   public List<AppointmentEntity> findAppointmentByRoomNumber(String roomNumber) {
      return appointmentRepository.findByRoomNumber(roomNumber);
   }

   @Override
   public List<AppointmentEntity> findAppointmentByDoctorName(String name) {
      return appointmentRepository.findByDoctorName(name);
   }

   @Override
   public AppointmentEntity findAppointmentById(Long appointmentId) {
      return appointmentRepository.findById(appointmentId)
         .orElseThrow( () -> new ObjectNotFoundException("Appointment not found with id " + appointmentId));
   }

   @Override
   public AppointmentEntity createAppointment(AppointmentRequest saveAppointment) {
      LocalDateTime appointmentTime = saveAppointment.getAppointmentTime();
      LocalDateTime startOfDay = appointmentTime.toLocalDate().atStartOfDay();
      LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

      ClinicEntity clinic = clinicService.findClinicById(saveAppointment.getClinicId());
      DoctorEntity doctor = doctorService.findDoctorById(saveAppointment.getDoctorId());

      if (appointmentRepository.existsByClinicAndAppointmentTime(clinic.getClinicId(), appointmentTime)) {
         throw new AppointmentException("El consultorio ya está ocupado a esa hora.");
      }

      if (appointmentRepository.existsByDoctorAndAppointmentTime(doctor.getDoctorId(), appointmentTime)) {
         throw new AppointmentException("El doctor ya tiene una cita a esa hora.");
      }

      if (appointmentRepository.existsPatientConflictSameDay(saveAppointment.getPatientName(), appointmentTime)) {
         throw new AppointmentException("El paciente tiene otra cita demasiado cercana.");
      }

      int citas = appointmentRepository.countAppointmentsForDoctorInDay(doctor.getDoctorId(), startOfDay, endOfDay);
      if (citas >= 8) {
         throw new AppointmentException("El doctor ya tiene 8 citas para ese día.");
      }

      AppointmentEntity appointment = new AppointmentEntity();
      appointment.setClinicId(saveAppointment.getClinicId());
      appointment.setDoctorId(saveAppointment.getDoctorId());
      appointment.setAppointmentTime(saveAppointment.getAppointmentTime());
      appointment.setPatientName(saveAppointment.getPatientName());
      appointment.setStatus(AppointmentStatus.PENDIENTE);
      return appointmentRepository.save(appointment);
   }

   @Override
   public AppointmentEntity updateAppointmentById(Long appointmentId, AppointmentRequest saveAppointment) {
      AppointmentEntity appointment = this.findAppointmentById(appointmentId);
      appointment.setClinicId(saveAppointment.getClinicId());
      appointment.setDoctorId(saveAppointment.getDoctorId());
      appointment.setAppointmentTime(saveAppointment.getAppointmentTime());
      appointment.setPatientName(saveAppointment.getPatientName());
      return appointmentRepository.save(appointment);
   }


}
