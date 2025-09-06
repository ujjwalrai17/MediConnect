package com.ujjwal.mediconnect.service;

import com.ujjwal.mediconnect.entity.Billing;
import java.util.List;

public interface BillingService {
    Billing generateBill(Billing billing, Long patientId);
    Billing getBillById(Long id);
    List<Billing> getAllBills();
    void deleteBill(Long id);
}
