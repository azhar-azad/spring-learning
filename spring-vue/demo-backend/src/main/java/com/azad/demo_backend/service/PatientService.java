package com.azad.demo_backend.service;

import com.azad.demo_backend.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {

    Patient addPatient(Patient patient);
    Patient getPatientById(Long id);
    List<Patient> getPatients();
    public Patient updatePatient(Patient patient);
    void deletePatient(Long id);
}
