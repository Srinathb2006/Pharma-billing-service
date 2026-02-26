package com.pharmacare.billing.dto;

import java.time.LocalDate;

public class MedicineResponse {

    private String id;
    private String name;
    private String category;
    private String batchNo;
    private LocalDate mfgDate;
    private LocalDate expiryDate;
    private Integer quantity;
    private String supplierId;
    private String supplierName;
    private String status;
    private Double unitPrice;

    public MedicineResponse() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public LocalDate getMfgDate() {
        return mfgDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getStatus() {
        return status;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public void setMfgDate(LocalDate mfgDate) {
        this.mfgDate = mfgDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}