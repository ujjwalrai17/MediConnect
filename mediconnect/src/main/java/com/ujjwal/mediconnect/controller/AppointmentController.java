package com.ujjwal.mediconnect.controller;

import com.ujjwal.mediconnect.entity.Appointment;
import com.ujjwal.mediconnect.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Operation(summary = "Book a new appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Appointment successfully booked",
                    content = @Content(schema = @Schema(implementation = Appointment.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Appointment> bookAppointment(
            @Parameter(description = "Appointment details") @RequestBody Appointment appointment,
            @Parameter(description = "Patient ID") @RequestParam Long patientId,
            @Parameter(description = "Doctor ID") @RequestParam Long doctorId) {
        Appointment savedAppointment = appointmentService.bookAppointment(appointment, patientId, doctorId);
        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    @Operation(summary = "Get appointment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment found",
                    content = @Content(schema = @Schema(implementation = Appointment.class))),
            @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(
            @Parameter(description = "ID of the appointment to retrieve") @PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @Operation(summary = "Get all appointments for a specific patient")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(
            @Parameter(description = "ID of the patient") @PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @Operation(summary = "Get all appointments for a specific doctor")
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(
            @Parameter(description = "ID of the doctor") @PathVariable Long doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @Operation(summary = "Update an existing appointment")
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @Parameter(description = "ID of the appointment to update") @PathVariable Long id,
            @Parameter(description = "Updated appointment details") @RequestBody Appointment appointmentDetails) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentDetails);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    @Operation(summary = "Cancel an appointment")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelAppointment(
            @Parameter(description = "ID of the appointment to cancel") @PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return new ResponseEntity<>("Appointment cancelled successfully", HttpStatus.OK);
    }
}
