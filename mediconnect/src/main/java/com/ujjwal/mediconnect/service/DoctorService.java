package com.ujjwal.mediconnect.service;

import com.ujjwal.mediconnect.entity.Doctor;
import java.util.List;

public interface DoctorService {
    Doctor registerDoctor(Doctor doctor);
    Doctor getDoctorById(Long id);
    Doctor getDoctorByEmail(String email);
    List<Doctor> getAllDoctors();
    Doctor updateDoctor(Long id, Doctor doctor);
    void deleteDoctor(Long id);
}
