package com.pharmacare.billing.entity;

public class BillItem {

    
    private String medicineId;

    
    private String medicineName;

    
    private Integer quantity;

    
    private Double price;

    
    private Double totalPrice;

   

    public BillItem() {}

    public BillItem(String medicineId, String medicineName,
                    Integer quantity, Double price, Double totalPrice) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
