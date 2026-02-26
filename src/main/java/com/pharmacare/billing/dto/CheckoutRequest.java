package com.pharmacare.billing.dto;

import java.util.List;

public class CheckoutRequest {

    private String customerName;
    private String customerPhone;
       
    private List<CartItemDto> items;

    public CheckoutRequest() {
    }

    

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public CheckoutRequest(String customerName, List<CartItemDto> items) {
        this.customerName = customerName;
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }
}