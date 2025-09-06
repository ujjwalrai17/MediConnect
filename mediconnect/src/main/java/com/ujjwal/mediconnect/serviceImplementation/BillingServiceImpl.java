package com.ujjwal.mediconnect.serviceImplementation;

import com.ujjwal.mediconnect.entity.Billing;
import com.ujjwal.mediconnect.entity.Appointment;
import com.ujjwal.mediconnect.entity.Patient;
import com.ujjwal.mediconnect.repository.BillingRepository;
import com.ujjwal.mediconnect.repository.AppointmentRepository;
import com.ujjwal.mediconnect.repository.PatientRepository;
import com.ujjwal.mediconnect.service.BillingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillingServiceImpl implements BillingService {

    private final BillingRepository billingRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public BillingServiceImpl(BillingRepository billingRepository,
                              AppointmentRepository appointmentRepository,
                              PatientRepository patientRepository) {
        this.billingRepository = billingRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Billing generateBill(Billing billing, Long patientId) {
        // Fetch patient
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));

        // Validate appointment
        Appointment appointment = billing.getAppointment();
        if (appointment == null || appointment.getPatient() == null ||
                !appointment.getPatient().getId().equals(patientId)) {
            throw new RuntimeException("Invalid appointment for the given patient");
        }

        billing.setBillingDate(LocalDateTime.now());
        billing.setStatus(billing.getStatus() == null ? "Pending" : billing.getStatus());

        return billingRepository.save(billing);
    }

    @Override
    public Billing getBillById(Long id) {
        return billingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with ID: " + id));
    }

    @Override
    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }

    @Override
    public void deleteBill(Long id) {
        Billing bill = billingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with ID: " + id));
        billingRepository.delete(bill);
    }
}
