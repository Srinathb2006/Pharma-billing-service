package com.pharmacare.billing.controller;

import com.pharmacare.billing.dto.CheckoutRequest;
import com.pharmacare.billing.entity.Bill;
import com.pharmacare.billing.service.BillingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PutMapping("/{id}/mark-paid")
    public ResponseEntity<String> markPaid(@PathVariable String id) {
        billingService.markPaid(id);
        return ResponseEntity.ok("Bill marked as PAID successfully");
    }

    @PostMapping("/checkout")
    public Bill checkout(@RequestBody CheckoutRequest request) {
        return billingService.checkout(request);
    }

    @GetMapping
    public List<Bill> getAllBills() {
        return billingService.getAllBills();
    }

    @GetMapping("/bills/paid-by-date")
    public List<Bill> getPaidBillsBetween(
            @RequestParam String from,
            @RequestParam String to
    ) {

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(23, 59, 59);

        return billingService.getPaidBillsBetween(fromDateTime, toDateTime);
    }

    @GetMapping("/{id}")
    public Bill getBillById(@PathVariable String id) {
        return billingService.getBillById(id);
    }

    @GetMapping("/test-feign/{id}")
    public String testFeign(@PathVariable String id) {
        billingService.testFeign(id);
        return "Feign call successful";
    }
}