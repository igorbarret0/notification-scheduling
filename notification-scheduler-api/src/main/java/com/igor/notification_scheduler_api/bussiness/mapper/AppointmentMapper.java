package com.igor.notification_scheduler_api.bussiness.mapper;

import com.igor.notification_scheduler_api.controller.dtos.in.AppointmentRecord;
import com.igor.notification_scheduler_api.controller.dtos.out.AppointmentRecordOut;
import com.igor.notification_scheduler_api.infrastrucutre.entities.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public Appointment toEntity(AppointmentRecord request) {


        Appointment response = new Appointment();
        response.setRecipientEmail(request.recipientEmail());
        response.setRecipientPhone(request.recipientPhone());
        response.setDateTimeOfSend(request.dateTimeOfSend());
        response.setMessage(request.message());


        return response;
    }

    public AppointmentRecordOut toAppointmentRecordOut(Appointment appointment) {

        AppointmentRecordOut response = new AppointmentRecordOut(
                appointment.getId(),
                appointment.getRecipientEmail(),
                appointment.getRecipientPhone(),
                appointment.getMessage(),
                appointment.getDateTimeOfSend(),
                appointment.getNotificationStatus()
        );

        return response;
    }

}
