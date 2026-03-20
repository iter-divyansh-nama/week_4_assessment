package com.capgemini.controller;

import com.capgemini.dto.AppointmentRequest;
import com.capgemini.dto.AppointmentResponse;
import com.capgemini.dto.PagedResponse;
import com.capgemini.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/api/doctors/{doctorId}/appointments")
    public ResponseEntity<AppointmentResponse> createAppointment(@PathVariable Long doctorId, @Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(doctorId, request));
    }

    @GetMapping("/api/doctors/{doctorId}/appointments")
    public ResponseEntity<PagedResponse<AppointmentResponse>> getAppointments(@PathVariable Long doctorId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctor(doctorId, page, size));
    }

    @GetMapping("/api/doctors/{doctorId}/appointments/{apptId}")
    public ResponseEntity<AppointmentResponse> getAppointment(@PathVariable Long doctorId, @PathVariable Long apptId) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(doctorId, apptId));
    }

    @PutMapping("/api/doctors/{doctorId}/appointments/{apptId}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable Long doctorId, @PathVariable Long apptId, @Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.updateAppointment(doctorId, apptId, request));
    }

    @DeleteMapping("/api/doctors/{doctorId}/appointments/{apptId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long doctorId, @PathVariable Long apptId) {
        appointmentService.deleteAppointment(doctorId, apptId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/appointments/pending")
    public ResponseEntity<List<AppointmentResponse>> getPendingAppointments() {
        return ResponseEntity.ok(appointmentService.getPendingAppointments());
    }
}
