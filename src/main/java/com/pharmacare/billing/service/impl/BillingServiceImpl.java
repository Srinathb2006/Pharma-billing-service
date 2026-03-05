package com.pharmacare.billing.service.impl;

import com.pharmacare.billing.client.InventoryFeignClient;
import com.pharmacare.billing.dto.CartItemDto;
import com.pharmacare.billing.dto.CheckoutRequest;
import com.pharmacare.billing.dto.StaffPerformanceDto;
import com.pharmacare.billing.entity.Bill;
import com.pharmacare.billing.entity.BillItem;
import com.pharmacare.billing.exception.BillingException;
import com.pharmacare.billing.repository.BillRespository;
import com.pharmacare.billing.service.BillingService;

import feign.FeignException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BillingServiceImpl implements BillingService {

    private final InventoryFeignClient inventoryFeignClient;
    private final BillRespository billRepository;

    public BillingServiceImpl(BillRespository billRepository,
            InventoryFeignClient inventoryFeignClient) {
        this.billRepository = billRepository;
        this.inventoryFeignClient = inventoryFeignClient;
    }

    @Override
    public Bill checkout(CheckoutRequest request) {

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BillingException("Cart is empty");
        }

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (email == null || email.equals("anonymousUser")) {
            throw new BillingException("Unauthorized: Staff not authenticated");
        }

        List<BillItem> billItems = new ArrayList<>();
        double grandTotal = 0.0;

        for (CartItemDto dto : request.getItems()) {

            if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
                throw new BillingException(
                        "Invalid quantity for medicine: " + dto.getMedicineName());
            }

            if (dto.getPrice() == null || dto.getPrice() < 0) {
                throw new BillingException(
                        "Invalid price for medicine: " + dto.getMedicineName());
            }

            try {
                inventoryFeignClient.deductStock(dto);

            } catch (FeignException.BadRequest e) {
                throw new BillingException(
                        "Insufficient stock for medicine: " + dto.getMedicineName());

            } catch (FeignException.NotFound e) {
                throw new BillingException(
                        "Medicine not found in inventory");

            } catch (FeignException e) {
                throw new BillingException(
                        "Inventory service error. Please try again later.");
            }

            double itemTotal = dto.getPrice() * dto.getQuantity();
            grandTotal += itemTotal;

            BillItem item = new BillItem();
            item.setMedicineId(dto.getMedicineId());
            item.setMedicineName(dto.getMedicineName());
            item.setQuantity(dto.getQuantity());
            item.setPrice(dto.getPrice());
            item.setTotalPrice(itemTotal);

            billItems.add(item);
        }

        double taxAmount = grandTotal * 0.05;
        double discountAmount = 0.0;
        double finalAmount = grandTotal + taxAmount - discountAmount;

        Bill bill = Bill.builder()
                .customerName(request.getCustomerName())
                .customerPhone(request.getCustomerPhone())
                .items(billItems)
                .totalAmount(grandTotal)
                .taxAmount(taxAmount)
                .discountAmount(discountAmount)
                .finalAmount(finalAmount)
                .paymentStatus("PENDING")
                .createdAt(LocalDateTime.now())
                .createdBy(email)
                .staffName(request.getStaffName())
                .staffId(request.getStaffId())
                .build();

        return billRepository.save(bill);
    }

    @Override
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @Override
    public Bill getBillById(String id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new BillingException("Bill not found with id: " + id));
    }

    @Override
    public void markPaid(String id) {

        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new BillingException("Bill not found with id: " + id));

        if (!"PENDING".equalsIgnoreCase(bill.getPaymentStatus())) {
            throw new BillingException("Only PENDING bills can be marked as PAID");
        }

        bill.setPaymentStatus("PAID");
        bill.setPaymentDate(LocalDateTime.now());

        billRepository.save(bill);
    }

    @Override
    public List<Bill> getPaidBillsBetween(LocalDateTime from, LocalDateTime to) {
        return billRepository.findByCreatedAtBetweenAndPaymentStatus(
                from,
                to,
                "PAID");
    }

    @Override
    public void testFeign(String medicineId) {
        var medicine = inventoryFeignClient.getMedicineById(medicineId);
        System.out.println("Medicine name: " + medicine.getName());
    }
}
