package com.pharmacare.billing.mapper;

import com.pharmacare.billing.dto.BillRequestDto;
import com.pharmacare.billing.dto.BillResponse;
import com.pharmacare.billing.entity.Bill;
import com.pharmacare.billing.entity.BillItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillMapper {

   
    public static Bill toEntity(BillRequestDto request,
                                List<BillItem> items,
                                Double totalAmount,
                                Double taxAmount,
                                Double discountAmount,
                                Double finalAmount) {

        return Bill.builder()
                .customerName(request.getCustomerName())
                .items(items)
                .totalAmount(totalAmount)
                .taxAmount(taxAmount)
                .discountAmount(discountAmount)
                .finalAmount(finalAmount)
                .paymentStatus("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
    }

   
    public static BillResponse toResponseDto(Bill bill) {
        if (bill == null) return null;

        BillResponse response = new BillResponse();
        response.setBillId(bill.getId());
        response.setAmount(bill.getTotalAmount());
        response.setTaxAmount(bill.getTaxAmount());
        response.setStatus(bill.getPaymentStatus());
        response.setCreatedAt(bill.getCreatedAt());

        
        List<BillResponse.ItemDto> itemDtos = new ArrayList<>();

        if (bill.getItems() != null) {
            for (BillItem item : bill.getItems()) {
                BillResponse.ItemDto dto = new BillResponse.ItemDto();
                dto.setProductId(item.getMedicineId());
                dto.setProductName(item.getMedicineName());
                dto.setQuantity(item.getQuantity());
                dto.setPrice(item.getPrice());
                dto.setTotal(item.getTotalPrice());

                itemDtos.add(dto);
            }
        }

        response.setItems(itemDtos);
        return response;
    }
}
