package com.ticket.tracking.repository;

import com.ticket.tracking.entity.BugType;
import com.ticket.tracking.entity.FeatureType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BugTypeRepository extends MongoRepository<BugType, String> {
    Optional<BugType> findById(String id);

    @Query("{'ticketType': {'$regex': ?0, '$options': 'i'}, '_id': ?1}")
    BugType findByTicketTypeByIdLikeIgnoreCase(String ticketType, String id);

    @Query("{'ticketType': {'$regex': ?0, '$options': 'i'}}")
    List<BugType> findByTicketTypeLikeIgnoreCase(String ticketType);
}
