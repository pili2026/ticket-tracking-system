package com.ticket.tracking.repository;

import com.ticket.tracking.entity.BugType;
import com.ticket.tracking.entity.FeatureType;
import com.ticket.tracking.entity.TestCaseType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestCaseRepository extends MongoRepository<TestCaseType, String> {
    Optional<TestCaseType> findById(String id);


    @Query("{'ticketType': {'$regex': ?0, '$options': 'i'}}")
    List<TestCaseType> findByTicketTypeLikeIgnoreCase(String ticketType);
}
