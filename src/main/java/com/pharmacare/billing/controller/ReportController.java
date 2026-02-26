package com.pharmacare.billing.controller;

import com.pharmacare.billing.dto.SalesReportRequest;
import com.pharmacare.billing.dto.StaffPerformanceDto;
import com.pharmacare.billing.service.ReportService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
		super();
		this.reportService = reportService;
	}
    @GetMapping("/staff-performance")
    public ResponseEntity<List<StaffPerformanceDto>> getStaffPerformance() {
        return ResponseEntity.ok(reportService.getStaffPerformance());
    }
    
	@PostMapping("/sales")
    public double getTotalSales(@RequestBody SalesReportRequest request) {
        return reportService.getTotalSales(request);
    }
}
