package com.ujjwal.mediconnect.serviceImplementation;

import com.ujjwal.mediconnect.entity.MedicalRecord;
import com.ujjwal.mediconnect.entity.Patient;
import com.ujjwal.mediconnect.repository.MedicalRecordRepository;
import com.ujjwal.mediconnect.repository.PatientRepository;
import com.ujjwal.mediconnect.service.MedicalRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository recordRepository;
    private final PatientRepository patientRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository recordRepository,
                                    PatientRepository patientRepository) {
        this.recordRepository = recordRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public MedicalRecord addRecord(MedicalRecord record, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));

        record.setPatient(patient);
        return recordRepository.save(record);
    }

    @Override
    public MedicalRecord getRecordById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found with ID: " + id));
    }

    @Override
    public List<MedicalRecord> getRecordsByPatient(Long patientId) {
        return recordRepository.findByPatientId(patientId);
    }

    @Override
    public MedicalRecord updateRecord(Long id, MedicalRecord recordDetails) {
        MedicalRecord record = getRecordById(id);
        record.setDiagnosis(recordDetails.getDiagnosis());
        record.setPrescription(recordDetails.getPrescription());
        record.setNotes(recordDetails.getNotes());
        return recordRepository.save(record);
    }

    @Override
    public void deleteRecord(Long id) {
        MedicalRecord record = getRecordById(id);
        recordRepository.delete(record);
    }
}

