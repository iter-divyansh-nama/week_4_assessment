package com.capgemini.service;

import com.capgemini.dto.DoctorRequest;
import com.capgemini.dto.DoctorResponse;
import com.capgemini.exception.DuplicateEmailException;
import com.capgemini.exception.ResourceNotFoundException;
import com.capgemini.model.Doctor;
import com.capgemini.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public DoctorResponse createDoctor(DoctorRequest request) {
        if (doctorRepository.existsByEmail(request.getEmail()))
            throw new DuplicateEmailException("Email already registered");

        Doctor doctor = new Doctor();
        doctor.setName(request.getName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());

        return toResponse(doctorRepository.save(doctor));
    }

    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public DoctorResponse getDoctorById(Long doctorId) {
        return toResponse(findDoctorOrThrow(doctorId));
    }

    public DoctorResponse updateDoctor(Long doctorId, DoctorRequest request) {
        Doctor doctor = findDoctorOrThrow(doctorId);

        if (!doctor.getEmail().equals(request.getEmail()) && doctorRepository.existsByEmail(request.getEmail()))
            throw new DuplicateEmailException("Email already registered");

        doctor.setName(request.getName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());

        return toResponse(doctorRepository.save(doctor));
    }

    public void deleteDoctor(Long doctorId) {
        findDoctorOrThrow(doctorId);
        doctorRepository.deleteById(doctorId);
    }

    public Doctor findDoctorOrThrow(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + doctorId + " not found"));
    }

    private DoctorResponse toResponse(Doctor doctor) {
        DoctorResponse response = new DoctorResponse();
        response.setDoctorId(doctor.getDoctorId());
        response.setName(doctor.getName());
        response.setSpecialization(doctor.getSpecialization());
        response.setEmail(doctor.getEmail());
        response.setPhone(doctor.getPhone());
        response.setTotalAppointments(doctor.getAppointments().size());
        return response;
    }
}
