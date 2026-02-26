package com.pharmacare.billing.dto;

public class StaffPerformanceDto {

    private String staffName;
    private double totalSales;
    private long billCount;
    private double averageBillValue;

    public StaffPerformanceDto() {
    }

    public StaffPerformanceDto(String staffName,
                               double totalSales,
                               long billCount,
                               double averageBillValue) {
        this.staffName = staffName;
        this.totalSales = totalSales;
        this.billCount = billCount;
        this.averageBillValue = averageBillValue;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public long getBillCount() {
        return billCount;
    }

    public void setBillCount(long billCount) {
        this.billCount = billCount;
    }

    public double getAverageBillValue() {
        return averageBillValue;
    }

    public void setAverageBillValue(double averageBillValue) {
        this.averageBillValue = averageBillValue;
    }
}