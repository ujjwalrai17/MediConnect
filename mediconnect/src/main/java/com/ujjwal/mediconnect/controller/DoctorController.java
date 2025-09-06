package com.ujjwal.mediconnect.controller;

import com.ujjwal.mediconnect.entity.Doctor;
import com.ujjwal.mediconnect.service.DoctorService;
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
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Operation(summary = "Register a new doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor successfully registered",
                    content = @Content(schema = @Schema(implementation = Doctor.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Doctor> registerDoctor(
            @Parameter(description = "Doctor details") @RequestBody Doctor doctor) {
        Doctor savedDoctor = doctorService.registerDoctor(doctor);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    @Operation(summary = "Get doctor by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Doctor.class))),
            @ApiResponse(responseCode = "404", description = "Doctor not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(
            @Parameter(description = "ID of the doctor to retrieve") @PathVariable Long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @Operation(summary = "Get doctor by email")
    @GetMapping("/email")
    public ResponseEntity<Doctor> getDoctorByEmail(
            @Parameter(description = "Email of the doctor to retrieve") @RequestParam String email) {
        Doctor doctor = doctorService.getDoctorByEmail(email);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @Operation(summary = "Get all doctors")
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @Operation(summary = "Update doctor details")
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(
            @Parameter(description = "ID of the doctor to update") @PathVariable Long id,
            @Parameter(description = "Updated doctor details") @RequestBody Doctor doctor) {
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }

    @Operation(summary = "Delete a doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Doctor not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(
            @Parameter(description = "ID of the doctor to delete") @PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>("Doctor deleted successfully", HttpStatus.OK);
    }
}
