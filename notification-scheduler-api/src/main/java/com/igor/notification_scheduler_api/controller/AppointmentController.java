package com.igor.notification_scheduler_api.controller;

import com.igor.notification_scheduler_api.bussiness.AppointmentService;
import com.igor.notification_scheduler_api.controller.dtos.in.AppointmentRecord;
import com.igor.notification_scheduler_api.controller.dtos.out.AppointmentRecordOut;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentRecordOut> saveAppointment(
            @RequestBody AppointmentRecord request
    ) {

        return ResponseEntity.ok(appointmentService.saveAppointment(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentRecordOut> findAppointmentById(
            @PathVariable(name = "id") Long id
    ) {

        return ResponseEntity.ok(appointmentService.findAppointmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> cancel_appointment(
            @PathVariable(name = "id") Long id
    ) {

        appointmentService.cancel_appointment(id);
        return ResponseEntity.accepted().build();
    }

}
