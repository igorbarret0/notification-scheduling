package com.igor.notification_scheduler_api.infrastrucutre.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String recipientEmail;

    private String recipientPhone;

    private LocalDateTime dateTimeOfSend;

    private LocalDateTime dateTimeOfAppointment;

    private LocalDateTime dateTimeOfModification;

    private String message;

    private NotificationStatus notificationStatus;

    public Appointment() {}

    public Appointment(Long id, String recipientEmail, String recipientPhone, LocalDateTime dateTimeOfSend, LocalDateTime dateTimeOfAppointment, LocalDateTime dateTimeOfModification, String message, NotificationStatus notificationStatus) {
        this.id = id;
        this.recipientEmail = recipientEmail;
        this.recipientPhone = recipientPhone;
        this.dateTimeOfSend = dateTimeOfSend;
        this.dateTimeOfAppointment = dateTimeOfAppointment;
        this.dateTimeOfModification = dateTimeOfModification;
        this.message = message;
        this.notificationStatus = notificationStatus;
    }

    @PrePersist
    private void prePersist() {

        dateTimeOfAppointment = LocalDateTime.now();
        notificationStatus = NotificationStatus.AGENDADO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public LocalDateTime getDateTimeOfSend() {
        return dateTimeOfSend;
    }

    public void setDateTimeOfSend(LocalDateTime dateTimeOfSend) {
        this.dateTimeOfSend = dateTimeOfSend;
    }

    public LocalDateTime getDateTimeOfAppointment() {
        return dateTimeOfAppointment;
    }

    public void setDateTimeOfAppointment(LocalDateTime dateTimeOfAppointment) {
        this.dateTimeOfAppointment = dateTimeOfAppointment;
    }

    public LocalDateTime getDateTimeOfModification() {
        return dateTimeOfModification;
    }

    public void setDateTimeOfModification(LocalDateTime dateTimeOfModification) {
        this.dateTimeOfModification = dateTimeOfModification;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
