package com.pharmacare.billing.service.impl;

import com.pharmacare.billing.dto.SalesReportRequest;
import com.pharmacare.billing.dto.StaffPerformanceDto;
import com.pharmacare.billing.repository.BillRespository;
import com.pharmacare.billing.service.ReportService;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final MongoTemplate mongoTemplate;
    private final BillRespository billRepository;

    public ReportServiceImpl(MongoTemplate mongoTemplate,
                             BillRespository billRepository) {
        this.mongoTemplate = mongoTemplate;
        this.billRepository = billRepository;
    }

    @Override
    public double getTotalSales(SalesReportRequest request) {

        LocalDate fromDate = request.getStartDate();
        LocalDate toDate = request.getEndDate();

        if (fromDate == null || toDate == null) {
            return 0.0;
        }

        LocalDateTime start = fromDate.atStartOfDay();
        LocalDateTime end = toDate.atTime(23, 59, 59);

        return billRepository
                .findByCreatedAtBetweenAndPaymentStatus(start, end, "PAID")
                .stream()
                .mapToDouble(b -> b.getFinalAmount())
                .sum();
    }

    @Override
    public List<StaffPerformanceDto> getStaffPerformance() {

        MatchOperation match = Aggregation.match(
                Criteria.where("paymentStatus").is("PAID")
                        .and("createdBy").ne(null)
        );

        GroupOperation group = Aggregation.group("createdBy")
                .sum("finalAmount").as("totalSales");

        ProjectionOperation project = Aggregation.project()
                .and("_id").as("staffName")
                .and("totalSales").as("totalSales");

        Aggregation aggregation = Aggregation.newAggregation(
                match,
                group,
                project
        );

        return mongoTemplate.aggregate(
                aggregation,
                "bills",
                StaffPerformanceDto.class
        ).getMappedResults();
    }
}