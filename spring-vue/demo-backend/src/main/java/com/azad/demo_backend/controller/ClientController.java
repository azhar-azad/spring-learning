package com.azad.demo_backend.controller;

import com.azad.demo_backend.model.Patient;
import com.azad.demo_backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {

    private final PatientService patientService;

    @Autowired
    public ClientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @RequestMapping("/")
    public String helloWorld() {
        return "Hello World";
    }

    @PostMapping("/add")
    public String addPatient(@RequestBody Patient patient) {
        patientService.addPatient(patient);
        return "Patient Added Successfully";
    }

    @RequestMapping("/patient/{id}")
    public Patient getPatientById(@PathVariable("id") Long id) {
        return patientService.getPatientById(id);
    }

    @RequestMapping("/patients")
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @PutMapping("/patient")
    public Patient updatePatient(@RequestBody Patient patient) {
        return patientService.updatePatient(patient);
    }

    @DeleteMapping("/patient/{id}")
    public String deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatient(id);
        return "Patient Deleted Successfully";
    }
}
