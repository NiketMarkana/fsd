package com.example;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
public class PatientController {

    @ModelAttribute("mainHeader")
    public String mainHeader() {
        return "Welcome to the best Clinic";
    }

    @ModelAttribute("countryList")
    public List<String> countryList() {
        return Arrays.asList("India", "USA", "Canada", "UK");
    }

    @ModelAttribute("allergyList")
    public List<String> allergyList() {
        return Arrays.asList("Peanuts", "Dust", "Smoke");
    }

    @GetMapping("/appointment")
    public ModelAndView appointment() {

        Patient patient = new Patient();
        patient.setPatientGender("Male");
        patient.setPatientAddress(new PatientAddress());

        return new ModelAndView("appointment", "patient", patient);
    }

    @PostMapping("/addappointment")
    public ModelAndView addAppointment(
            @Valid @ModelAttribute("patient") Patient patient,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("appointment", "patient", patient);
        }

        ModelAndView modelAndView = new ModelAndView("addappointment");
        modelAndView.addObject("patient", patient);
        modelAndView.addObject("message", "We have successfully registered your details");

        return modelAndView;
    }
}