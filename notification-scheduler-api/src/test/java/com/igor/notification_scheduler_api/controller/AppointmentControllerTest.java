package com.igor.notification_scheduler_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.igor.notification_scheduler_api.bussiness.AppointmentService;
import com.igor.notification_scheduler_api.controller.dtos.in.AppointmentRecord;
import com.igor.notification_scheduler_api.controller.dtos.out.AppointmentRecordOut;
import com.igor.notification_scheduler_api.infrastrucutre.entities.NotificationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AppointmentControllerTest {

    @Mock
    AppointmentService appointmentService;

    @InjectMocks
    AppointmentController appointmentController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private AppointmentRecord appointmentRecord;
    private AppointmentRecordOut appointmentRecordOut;

    @BeforeEach
    void setUp() {

        mockMvc= MockMvcBuilders.standaloneSetup(appointmentController).build();
        objectMapper.registerModule(new JavaTimeModule());

        appointmentRecord = new AppointmentRecord(
                "email@email.com",
                "234820984",
                LocalDateTime.of(2025, 01, 02, 11, 01, 01),
                "Favor retornar a loja"
        );

        appointmentRecordOut = new AppointmentRecordOut(
          1L,
                "email@email.com",
                "234820984",
                "Favor retornar a loja",
                LocalDateTime.of(2025, 01, 02, 11, 01, 01),
                NotificationStatus.AGENDADO
        );
    }

    @Test
    void shouldSaveAppointmentSuccessfully() throws Exception {

        when(appointmentService.saveAppointment(appointmentRecord))
                .thenReturn(appointmentRecordOut);

        mockMvc.perform(post("/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentRecord)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.recipientEmail").value("email@email.com"))
                .andExpect(jsonPath("$.recipientPhone").value(234820984))
                .andExpect(jsonPath("$.message").value("Favor retornar a loja"))
                .andExpect(jsonPath("$.dateTimeOfSend").value("02-01-2025 11:01:01"))
                .andExpect(jsonPath("$.notificationStatus").value("AGENDADO"));

        verify(appointmentService, times(1)).saveAppointment(appointmentRecord);
    }

    @Test
    void shouldReturnAppointmentById() throws Exception {

        when(appointmentService.findAppointmentById(anyLong()))
                .thenReturn(appointmentRecordOut);

        mockMvc.perform(get("/appointment/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.recipientEmail").value("email@email.com"))
                .andExpect(jsonPath("$.recipientPhone").value(234820984))
                .andExpect(jsonPath("$.message").value("Favor retornar a loja"))
                .andExpect(jsonPath("$.dateTimeOfSend").value("02-01-2025 11:01:01"))
                .andExpect(jsonPath("$.notificationStatus").value("AGENDADO"));

        verify(appointmentService, times(1)).findAppointmentById(anyLong());
    }

    @Test
    void shouldCancelAppointmentSuccessfully() throws Exception {

        doNothing().when(appointmentService).cancel_appointment(anyLong());

        mockMvc.perform(put("/appointment/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        verify(appointmentService, times(1)).cancel_appointment(anyLong());
    }

}
