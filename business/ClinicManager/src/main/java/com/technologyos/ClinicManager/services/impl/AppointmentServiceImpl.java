package com.technologyos.ClinicManager.services.impl;

import com.technologyos.ClinicManager.dtos.request.AppointmentRequest;
import com.technologyos.ClinicManager.entities.AppointmentEntity;
import com.technologyos.ClinicManager.exceptions.ObjectNotFoundException;
import com.technologyos.ClinicManager.repositories.AppointmentRepository;
import com.technologyos.ClinicManager.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

   private final AppointmentRepository appointmentRepository;

   @Override
   public Page<AppointmentEntity> findAll(Pageable pageable) {
      return appointmentRepository.findAll(pageable);
   }

   @Override
   public AppointmentEntity findAppointmentById(Long appointmentId) {
      return appointmentRepository.findById(appointmentId)
         .orElseThrow( () -> new ObjectNotFoundException("Appointment not found with id " + appointmentId));
   }

   @Override
   public AppointmentEntity createAppointment(AppointmentRequest saveAppointment) {
      AppointmentEntity appointment = new AppointmentEntity();
      appointment.setClinicId(saveAppointment.getClinicId());
      appointment.setDoctorId(saveAppointment.getDoctorId());
      appointment.setAppointmentTime(saveAppointment.getAppointmentTime());
      appointment.setPatientName(saveAppointment.getPatientName());
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
