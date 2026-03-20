package com.capgemini.repository;

import com.capgemini.model.Appointment;
import com.capgemini.model.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findByDoctorDoctorId(Long doctorId, Pageable pageable);
    Optional<Appointment> findByAppointmentIdAndDoctorDoctorId(Long appointmentId, Long doctorId);
    List<Appointment> findByStatusOrderByScheduledTimeAsc(AppointmentStatus status);
}
