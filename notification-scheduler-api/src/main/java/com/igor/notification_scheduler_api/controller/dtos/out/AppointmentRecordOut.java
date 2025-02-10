package com.igor.notification_scheduler_api.controller.dtos.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.igor.notification_scheduler_api.infrastrucutre.entities.NotificationStatus;

import java.time.LocalDateTime;

public record AppointmentRecordOut(
        Long id,
        String recipientEmail,
        String recipientPhone,
        String message,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")LocalDateTime dateTimeOfSend,
        NotificationStatus notificationStatus
        ) {
}
