package com.pharmacare.billing.repository;
import org.springframework.data.repository.query.Param;
import com.pharmacare.billing.dto.StaffPerformanceDto;
import com.pharmacare.billing.entity.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface BillRespository extends MongoRepository<Bill, String> {
	

	List<Bill> findByCreatedAtBetweenAndPaymentStatus(
	        LocalDateTime from,
	        LocalDateTime to,
	        String paymentStatus
	);
	
			
	
	
    List<Bill> findByPaymentStatus(String paymentStatus);
    
}
