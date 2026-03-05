package com.pharmacare.billing.service;

import com.pharmacare.billing.entity.Bill;
import com.pharmacare.billing.repository.BillRespository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final BillRespository billRepository;

    public DashboardService(BillRespository billRepository) {
        this.billRepository = billRepository;
    }

    public Map<String, Object> getSummary() {
        List<Bill> paidBills = billRepository.findAll().stream()
                .filter(b -> "PAID".equalsIgnoreCase(b.getPaymentStatus()))
                .collect(Collectors.toList());

        double totalSales = paidBills.stream()
                .mapToDouble(Bill::getFinalAmount)
                .sum();

        long totalTransactions = paidBills.size();

        // Monthly sales for the current year
        int currentYear = LocalDate.now().getYear();
        Map<Month, Double> monthlySalesMap = new EnumMap<>(Month.class);
        for (Month month : Month.values()) {
            monthlySalesMap.put(month, 0.0);
        }

        paidBills.stream()
                .filter(b -> b.getCreatedAt() != null && b.getCreatedAt().getYear() == currentYear)
                .forEach(b -> {
                    Month month = b.getCreatedAt().getMonth();
                    monthlySalesMap.put(month, monthlySalesMap.get(month) + b.getFinalAmount());
                });

        List<Map<String, Object>> monthlySales = Arrays.stream(Month.values())
                .map(m -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("month", m.name().substring(0, 3));
                    map.put("sales", monthlySalesMap.get(m));
                    return map;
                })
                .collect(Collectors.toList());

        // Staff sales
        Map<String, Double> staffSales = paidBills.stream()
                .filter(b -> b.getCreatedBy() != null)
                .collect(Collectors.groupingBy(
                        b -> b.getStaffName() != null ? b.getStaffName() : b.getCreatedBy(),
                        Collectors.summingDouble(Bill::getFinalAmount)));

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalSales", totalSales);
        summary.put("totalTransactions", totalTransactions);
        summary.put("monthlySales", monthlySales);
        summary.put("staffSales", staffSales);

        return summary;
    }
}
