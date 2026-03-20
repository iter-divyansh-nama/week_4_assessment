package com.capgemini.service;

import com.capgemini.dto.AppointmentRequest;
import com.capgemini.dto.AppointmentResponse;
import com.capgemini.dto.PagedResponse;
import com.capgemini.exception.ResourceNotFoundException;
import com.capgemini.model.Appointment;
import com.capgemini.model.AppointmentStatus;
import com.capgemini.model.Doctor;
import com.capgemini.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorService doctorService;

    public AppointmentResponse createAppointment(Long doctorId, AppointmentRequest request) {
        Doctor doctor = doctorService.findDoctorOrThrow(doctorId);

        Appointment appointment = new Appointment();
        appointment.setPatientName(request.getPatientName());
        appointment.setScheduledTime(request.getScheduledTime());
        appointment.setStatus(request.getStatus());
        appointment.setDoctor(doctor);

        return toResponse(appointmentRepository.save(appointment));
    }

    public PagedResponse<AppointmentResponse> getAppointmentsByDoctor(Long doctorId, int page, int size) {
        doctorService.findDoctorOrThrow(doctorId);

        Pageable pageable = PageRequest.of(page, size, Sort.by("scheduledTime").ascending());
        Page<Appointment> apptPage = appointmentRepository.findByDoctorDoctorId(doctorId, pageable);

        PagedResponse<AppointmentResponse> response = new PagedResponse<>();
        response.setContent(apptPage.getContent().stream().map(this::toResponse).collect(Collectors.toList()));
        response.setPageNumber(apptPage.getNumber());
        response.setPageSize(apptPage.getSize());
        response.setTotalElements(apptPage.getTotalElements());
        response.setTotalPages(apptPage.getTotalPages());
        response.setLast(apptPage.isLast());
        return response;
    }

    public AppointmentResponse getAppointmentById(Long doctorId, Long apptId) {
        doctorService.findDoctorOrThrow(doctorId);
        Appointment appointment = findAppointmentOrThrow(doctorId, apptId);
        return toResponse(appointment);
    }

    public AppointmentResponse updateAppointment(Long doctorId, Long apptId, AppointmentRequest request) {
        doctorService.findDoctorOrThrow(doctorId);
        Appointment appointment = findAppointmentOrThrow(doctorId, apptId);

        appointment.setPatientName(request.getPatientName());
        appointment.setScheduledTime(request.getScheduledTime());
        appointment.setStatus(request.getStatus());

        return toResponse(appointmentRepository.save(appointment));
    }

    public void deleteAppointment(Long doctorId, Long apptId) {
        doctorService.findDoctorOrThrow(doctorId);
        Appointment appointment = findAppointmentOrThrow(doctorId, apptId);
        appointmentRepository.delete(appointment);
    }

    public List<AppointmentResponse> getPendingAppointments() {
        return appointmentRepository.findByStatusOrderByScheduledTimeAsc(AppointmentStatus.PENDING)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    private Appointment findAppointmentOrThrow(Long doctorId, Long apptId) {
        return appointmentRepository.findByAppointmentIdAndDoctorDoctorId(apptId, doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment with ID " + apptId + " not found"));
    }

    private AppointmentResponse toResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setAppointmentId(appointment.getAppointmentId());
        response.setPatientName(appointment.getPatientName());
        response.setScheduledTime(appointment.getScheduledTime());
        response.setStatus(appointment.getStatus());
        return response;
    }
}
