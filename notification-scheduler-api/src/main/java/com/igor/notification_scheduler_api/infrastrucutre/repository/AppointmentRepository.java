package com.igor.notification_scheduler_api.infrastrucutre.repository;

import com.igor.notification_scheduler_api.infrastrucutre.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
