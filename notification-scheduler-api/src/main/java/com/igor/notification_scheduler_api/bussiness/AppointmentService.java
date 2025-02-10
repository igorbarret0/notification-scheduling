package com.igor.notification_scheduler_api.bussiness;

import com.igor.notification_scheduler_api.bussiness.mapper.AppointmentMapper;
import com.igor.notification_scheduler_api.controller.dtos.in.AppointmentRecord;
import com.igor.notification_scheduler_api.controller.dtos.out.AppointmentRecordOut;
import com.igor.notification_scheduler_api.infrastrucutre.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

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

}
