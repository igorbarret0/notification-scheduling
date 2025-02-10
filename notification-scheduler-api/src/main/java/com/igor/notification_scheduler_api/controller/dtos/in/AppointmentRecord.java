package com.igor.notification_scheduler_api.controller.dtos.in;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record AppointmentRecord(

        String recipientEmail,
        String recipientPhone,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime dateTimeOfSend,
        String message

) {
}
