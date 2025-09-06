package com.ujjwal.mediconnect.service;

import com.ujjwal.mediconnect.entity.Patient;
import java.util.List;

public interface PatientService {
    Patient registerPatient(Patient patient);
    Patient getPatientById(Long id);
    Patient getPatientByEmail(String email);
    List<Patient> getAllPatients();
    Patient updatePatient(Long id, Patient patient);
    void deletePatient(Long id);
}
