package com.example;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class Patient {

    @Pattern(
            regexp = "^[a-zA-Z. ]+$",
            message = "Patient name should only contain alphabets and dots."
    )
    private String patientName;

    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Please enter a valid 10-digit mobile number starting with 6."
    )
    private String patientContact;

    @Pattern(
            regexp = "^(Male|Female)$",
            message = "Please select a valid gender (Male or Female)."
    )
    private String patientGender;

    @NotNull(message = "Birthdate cannot be empty")
    @Past(message = "Please enter a valid past date for the birthdate.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date patientDoB;

    private List<String> patientAllergies;

    @Valid
    private PatientAddress patientAddress;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientContact() {
        return patientContact;
    }

    public void setPatientContact(String patientContact) {
        this.patientContact = patientContact;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public Date getPatientDoB() {
        return patientDoB;
    }

    public void setPatientDoB(Date patientDoB) {
        this.patientDoB = patientDoB;
    }

    public List<String> getPatientAllergies() {
        return patientAllergies;
    }

    public void setPatientAllergies(List<String> patientAllergies) {
        this.patientAllergies = patientAllergies;
    }

    public PatientAddress getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(PatientAddress patientAddress) {
        this.patientAddress = patientAddress;
    }
}