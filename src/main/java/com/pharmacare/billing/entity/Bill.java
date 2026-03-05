package com.pharmacare.billing.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "bills")
public class Bill {

    @Id
    private String id;

    // ================= Customer Info =================
    private String customerName;
    private String customerPhone;

    private String pharmacistId;
    private String staffId;
    private String staffName;
    // ================= Line Items =================
    private List<BillItem> items;

    public String getPharmacistId() {
        return pharmacistId;
    }

    public void setPharmacistId(String pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ================= Pricing =================
    private Double totalAmount;
    private Double taxAmount;
    private Double discountAmount;
    private Double finalAmount;

    // ================= Payment =================
    private String paymentStatus; // PENDING, PAID, FAILED

    // ================= Optional =================
    private String prescriptionId;

    // ================= Audit =================
    private LocalDateTime createdAt;
    // ================= Audit =================

    private String createdBy;
    private LocalDateTime paymentDate;

    // ================= Private Constructor =================
    private Bill(Builder builder) {
        this.id = builder.id;
        this.customerName = builder.customerName;
        this.customerPhone = builder.customerPhone;
        this.items = builder.items;
        this.totalAmount = builder.totalAmount;
        this.taxAmount = builder.taxAmount;
        this.discountAmount = builder.discountAmount;
        this.finalAmount = builder.finalAmount;
        this.paymentStatus = builder.paymentStatus;
        this.prescriptionId = builder.prescriptionId;
        this.createdAt = builder.createdAt;
        this.createdBy = builder.createdBy;
        this.staffName = builder.staffName;
        this.staffId = builder.staffId;
        this.paymentDate = builder.paymentDate;
    }

    // Required by MongoDB
    public Bill() {
    }

    // ================= Builder =================

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String createdBy;
        private String staffName;
        private String staffId;
        private String id;
        private String customerName;
        private String customerPhone;
        private List<BillItem> items;
        private Double totalAmount;
        private Double taxAmount;
        private Double discountAmount;
        private Double finalAmount;
        private String paymentStatus;
        private String prescriptionId;
        private LocalDateTime createdAt;
        private LocalDateTime paymentDate;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder staffName(String staffName) {
            this.staffName = staffName;
            return this;
        }

        public Builder staffId(String staffId) {
            this.staffId = staffId;
            return this;
        }

        public Builder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder customerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
            return this;
        }

        public Builder items(List<BillItem> items) {
            this.items = items;
            return this;
        }

        public Builder totalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder taxAmount(Double taxAmount) {
            this.taxAmount = taxAmount;
            return this;
        }

        public Builder discountAmount(Double discountAmount) {
            this.discountAmount = discountAmount;
            return this;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public Builder finalAmount(Double finalAmount) {
            this.finalAmount = finalAmount;
            return this;
        }

        public Builder paymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder prescriptionId(String prescriptionId) {
            this.prescriptionId = prescriptionId;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder paymentDate(LocalDateTime paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public Bill build() {
            return new Bill(this);
        }
    }

    // ================= Getters =================

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public Double getFinalAmount() {
        return finalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    // ================= Setters (Mutable Fields Only) =================

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}
