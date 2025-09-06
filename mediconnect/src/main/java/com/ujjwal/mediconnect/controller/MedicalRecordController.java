package com.ujjwal.mediconnect.controller;

import com.ujjwal.mediconnect.entity.MedicalRecord;
import com.ujjwal.mediconnect.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @Operation(summary = "Add a new medical record for a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medical record successfully added",
                    content = @Content(schema = @Schema(implementation = MedicalRecord.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<MedicalRecord> addRecord(
            @Parameter(description = "Medical record details") @RequestBody MedicalRecord record,
            @Parameter(description = "ID of the patient") @RequestParam Long patientId) {
        MedicalRecord savedRecord = medicalRecordService.addRecord(record, patientId);
        return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a medical record by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical record retrieved successfully",
                    content = @Content(schema = @Schema(implementation = MedicalRecord.class))),
            @ApiResponse(responseCode = "404", description = "Medical record not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> getRecordById(
            @Parameter(description = "ID of the medical record") @PathVariable Long id) {
        MedicalRecord record = medicalRecordService.getRecordById(id);
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    @Operation(summary = "Get all medical records for a specific patient")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecord>> getRecordsByPatient(
            @Parameter(description = "ID of the patient") @PathVariable Long patientId) {
        List<MedicalRecord> records = medicalRecordService.getRecordsByPatient(patientId);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @Operation(summary = "Update a medical record by ID")
    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecord> updateRecord(
            @Parameter(description = "ID of the medical record to update") @PathVariable Long id,
            @Parameter(description = "Updated medical record details") @RequestBody MedicalRecord recordDetails) {
        MedicalRecord updatedRecord = medicalRecordService.updateRecord(id, recordDetails);
        return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
    }

    @Operation(summary = "Delete a medical record by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical record deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Medical record not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecord(
            @Parameter(description = "ID of the medical record to delete") @PathVariable Long id) {
        medicalRecordService.deleteRecord(id);
        return new ResponseEntity<>("Medical record deleted successfully", HttpStatus.OK);
    }
}
