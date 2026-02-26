package com.pharmacare.billing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.pharmacare.billing.entity.Prescription;

public interface PrescriptionRepository extends MongoRepository<Prescription, String> {
}
