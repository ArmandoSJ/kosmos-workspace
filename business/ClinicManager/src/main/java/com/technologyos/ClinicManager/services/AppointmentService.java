package com.technologyos.ClinicManager.services;

import com.technologyos.ClinicManager.dtos.request.AppointmentRequest;
import com.technologyos.ClinicManager.entities.AppointmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppointmentService {
   Page<AppointmentEntity> findAll(Pageable pageable);

   List<AppointmentEntity> findAppointmentByRoomNumber(String roomNumber);

   List<AppointmentEntity> findAppointmentByDoctorName(String name);

   AppointmentEntity findAppointmentById(Long appointmentId);

   AppointmentEntity createAppointment(AppointmentRequest saveAppointment);

   AppointmentEntity updateAppointmentById(Long appointmentId, AppointmentRequest saveAppointment);
}
