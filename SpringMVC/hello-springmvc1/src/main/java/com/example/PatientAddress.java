package com.example;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PatientAddress {

    @Size(
            min = 3,
            max = 40,
            message = "Street must contain minimum 3 characters and maximum 40 characters"
    )
    private String street;

    @NotNull(message = "City cannot be empty")
    @NotEmpty(message = "City cannot be empty")
    private String city;

    @NotBlank(message = "State cannot be blank")
    private String state;

    @Pattern(
            regexp = "^(India|USA|Canada|UK)$",
            message = "Please select a valid Country from the list."
    )
    private String country;

    @NotNull(message = "Pincode cannot be empty")
    @Min(value = 1000, message = "Pincode must be greater than or equal to 1000.")
    @Max(value = 999999, message = "Pincode must be less than or equal to 999999.")
    private Integer pincode;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }
}