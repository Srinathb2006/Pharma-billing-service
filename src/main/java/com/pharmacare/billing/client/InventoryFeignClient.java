package com.pharmacare.billing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.pharmacare.billing.dto.MedicineResponse;   
import com.pharmacare.billing.dto.CartItemDto;

@FeignClient(
        name = "inventory-service",
        url = "${inventory.service.url}"
)
public interface InventoryFeignClient {

    @PostMapping("/api/medicines/deduct")
    void deductStock(@RequestBody CartItemDto item);

    @GetMapping("/api/medicines/{id}")
    MedicineResponse getMedicineById(@PathVariable("id") String id);
}