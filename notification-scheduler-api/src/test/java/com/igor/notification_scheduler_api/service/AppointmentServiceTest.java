package com.igor.notification_scheduler_api.service;

import com.igor.notification_scheduler_api.bussiness.AppointmentService;
import com.igor.notification_scheduler_api.bussiness.mapper.AppointmentMapper;
import com.igor.notification_scheduler_api.controller.dtos.in.AppointmentRecord;
import com.igor.notification_scheduler_api.controller.dtos.out.AppointmentRecordOut;
import com.igor.notification_scheduler_api.infrastrucutre.entities.Appointment;
import com.igor.notification_scheduler_api.infrastrucutre.entities.NotificationStatus;
import com.igor.notification_scheduler_api.infrastrucutre.exception.NotFoundException;
import com.igor.notification_scheduler_api.infrastrucutre.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    AppointmentMapper appointmentMapper;

    @InjectMocks
    AppointmentService appointmentService;

    private AppointmentRecord appointmentRecord;
    private AppointmentRecordOut appointmentRecordOut;
    private Appointment appointment;

    @BeforeEach
    void setUp() {

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

        appointment = new Appointment(
                1L,
                "email@email.com",
                "234820984",
                LocalDateTime.of(2025, 01, 02, 11, 01, 01),
                LocalDateTime.now(),
                null,
                "Favor retornar a loja",
                NotificationStatus.AGENDADO
        );
    }

    @Test
    void shouldSaveAppointmentSuccessfully() {

        when(appointmentMapper.toEntity(appointmentRecord))
                .thenReturn(appointment);

        when(appointmentRepository.save(appointment))
                .thenReturn(appointment);

        when(appointmentMapper.toAppointmentRecordOut(appointment))
                .thenReturn(appointmentRecordOut);

        AppointmentRecordOut response = appointmentService.saveAppointment(appointmentRecord);

        assertEquals(appointmentRecordOut.id(), response.id());
        assertEquals(appointmentRecordOut.recipientEmail(), response.recipientEmail());
        assertEquals(appointmentRecordOut.recipientPhone(), response.recipientPhone());
        assertEquals(appointmentRecordOut.dateTimeOfSend(), response.dateTimeOfSend());
        assertEquals(appointmentRecordOut.notificationStatus(), response.notificationStatus());

        verify(appointmentMapper, times(1)).toEntity(appointmentRecord);
        verify(appointmentRepository, times(1)).save(appointment);
        verify(appointmentMapper, times(1)).toAppointmentRecordOut(appointment);
    }

    @Test
    void shouldFoundAppointmentWithValidIdSuccessfully() {


        when(appointmentRepository.findById(anyLong()))
                .thenReturn(Optional.of(appointment));

        when(appointmentMapper.toAppointmentRecordOut(appointment))
                .thenReturn(appointmentRecordOut);

        var response = appointmentService.findAppointmentById(1L);

        assertEquals(appointmentRecordOut.id(), response.id());
        assertEquals(appointmentRecordOut.recipientEmail(), response.recipientEmail());
        assertEquals(appointmentRecordOut.recipientPhone(), response.recipientPhone());
        assertEquals(appointmentRecordOut.message(), response.message());
        assertEquals(appointmentRecordOut.dateTimeOfSend(), response.dateTimeOfSend());
        assertEquals(appointmentRecordOut.notificationStatus(), response.notificationStatus());

        verify(appointmentRepository, times(1)).findById(1L);
        verify(appointmentMapper, times(1)).toAppointmentRecordOut(appointment);
    }

    @Test
    void shouldThrowExceptionWhenTryToFindAppointmentWithInvalidId() {

        when(appointmentRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(NotFoundException.class, () -> {
            appointmentService.findAppointmentById(99L);
        });

        assertEquals("Could not found a appointment with the provided ID: " + 99L, exception.getMessage());
    }

    @Test
    void shouldCancelTheAppointment() {

        when(appointmentRepository.findById(anyLong()))
                .thenReturn(Optional.of(appointment));

        ArgumentCaptor<Appointment> captor = ArgumentCaptor.forClass(Appointment.class);
        appointmentService.cancel_appointment(1L);

        verify(appointmentRepository).save(captor.capture());
        Appointment savedAppointment = captor.getValue();

        assertEquals(NotificationStatus.CANCELADO, savedAppointment.getNotificationStatus());
        assertNotNull(savedAppointment.getDateTimeOfModification());
    }

    @Test
    void shouldThrowExceptionWhenTryToCancelAppointmentWhoDoesNotExists() {

        when(appointmentRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.cancel_appointment(99L);
        });

        assertEquals("Could not found a appointment with the provided ID: " + 99L, exception.getMessage());
    }
}
