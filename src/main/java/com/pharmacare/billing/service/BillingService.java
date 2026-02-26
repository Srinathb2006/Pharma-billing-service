package com.pharmacare.billing.service;

import com.pharmacare.billing.dto.CheckoutRequest;
import com.pharmacare.billing.entity.Bill;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface BillingService {

    Bill checkout(CheckoutRequest request);

    List<Bill> getAllBills();

    Bill getBillById(String id);
    
    

    List<Bill> getPaidBillsBetween(LocalDateTime from, LocalDateTime to);
   

    
    void markPaid(String id);

    void testFeign(String medicineId);
    
}
