package com.igor.notification_scheduler_api.bussiness;

import com.igor.notification_scheduler_api.bussiness.mapper.AppointmentMapper;
import com.igor.notification_scheduler_api.controller.dtos.in.AppointmentRecord;
import com.igor.notification_scheduler_api.controller.dtos.out.AppointmentRecordOut;
import com.igor.notification_scheduler_api.infrastrucutre.entities.NotificationStatus;
import com.igor.notification_scheduler_api.infrastrucutre.exception.NotFoundException;
import com.igor.notification_scheduler_api.infrastrucutre.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private AppointmentMapper appointmentMapper;

    public AppointmentService(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
    }

    public AppointmentRecordOut saveAppointment(AppointmentRecord request) {

        var response = appointmentMapper.toEntity(request);
        appointmentRepository.save(response);

        return appointmentMapper.toAppointmentRecordOut(response);
    }

    public AppointmentRecordOut findAppointmentById(Long id) {

        var appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found a appointment with the provided ID: " + id));

        return appointmentMapper.toAppointmentRecordOut(appointment);
    }

    public void cancel_appointment(Long id) {

        var appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not found a appointment with the provided ID: " + id));

        appointment.setDateTimeOfModification(LocalDateTime.now());
        appointment.setNotificationStatus(NotificationStatus.CANCELADO);
        appointmentRepository.save(appointment);
    }

}
