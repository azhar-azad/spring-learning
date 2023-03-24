package com.azad.service.doctor;

import com.azad.dao.doctor.DoctorRepository;
import com.azad.model.doctor.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository repository;

    @PostConstruct
    public void initDoctor() {
        repository.saveAll(Stream.of(
                new Doctor(101L, "John", "Cardiac"),
                        new Doctor(102L, "Doe", "Eye"))
                .collect(Collectors.toList()));
    }

    public List<Doctor> getDoctors() {
        return repository.findAll();
    }
}
