package com.ujjwal.mediconnect.controller;

import com.ujjwal.mediconnect.entity.Billing;
import com.ujjwal.mediconnect.service.BillingService;
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
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @Operation(summary = "Generate a bill for a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bill successfully generated",
                    content = @Content(schema = @Schema(implementation = Billing.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Billing> generateBill(
            @Parameter(description = "Billing details") @RequestBody Billing billing,
            @Parameter(description = "Patient ID") @RequestParam Long patientId) {
        Billing savedBill = billingService.generateBill(billing, patientId);
        return new ResponseEntity<>(savedBill, HttpStatus.CREATED);
    }

    @Operation(summary = "Get bill by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Billing.class))),
            @ApiResponse(responseCode = "404", description = "Bill not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Billing> getBillById(
            @Parameter(description = "ID of the bill to retrieve") @PathVariable Long id) {
        Billing bill = billingService.getBillById(id);
        return new ResponseEntity<>(bill, HttpStatus.OK);
    }

    @Operation(summary = "Get all bills")
    @GetMapping
    public ResponseEntity<List<Billing>> getAllBills() {
        List<Billing> bills = billingService.getAllBills();
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    @Operation(summary = "Delete a bill")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bill not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBill(
            @Parameter(description = "ID of the bill to delete") @PathVariable Long id) {
        billingService.deleteBill(id);
        return new ResponseEntity<>("Bill deleted successfully", HttpStatus.OK);
    }
}
