package com.ujjwal.mediconnect.service;

import com.ujjwal.mediconnect.entity.MedicalRecord;
import java.util.List;

public interface MedicalRecordService {
    MedicalRecord addRecord(MedicalRecord record, Long patientId);
    MedicalRecord getRecordById(Long id);
    List<MedicalRecord> getRecordsByPatient(Long patientId);
    MedicalRecord updateRecord(Long id, MedicalRecord record);
    void deleteRecord(Long id);
}
