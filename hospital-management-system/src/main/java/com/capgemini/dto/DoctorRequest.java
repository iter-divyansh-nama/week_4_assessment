package com.capgemini.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class DoctorRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String specialization;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    public DoctorRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
