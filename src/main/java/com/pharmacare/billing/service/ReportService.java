package com.pharmacare.billing.service;

import java.time.LocalDateTime;
import java.util.List;

import com.pharmacare.billing.dto.SalesReportRequest;
import com.pharmacare.billing.dto.StaffPerformanceDto;

public interface ReportService {
	List<StaffPerformanceDto> getStaffPerformance();
    double getTotalSales(SalesReportRequest request);
  
}
