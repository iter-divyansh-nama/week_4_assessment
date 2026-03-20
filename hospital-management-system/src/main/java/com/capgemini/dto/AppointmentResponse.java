package com.capgemini.dto;

import com.capgemini.model.AppointmentStatus;
import java.time.LocalDateTime;

public class AppointmentResponse {

    private Long appointmentId;
    private String patientName;
    private LocalDateTime scheduledTime;
    private AppointmentStatus status;

    public AppointmentResponse() {}

    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public LocalDateTime getScheduledTime() { return scheduledTime; }
    public void setScheduledTime(LocalDateTime scheduledTime) { this.scheduledTime = scheduledTime; }
    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
}
