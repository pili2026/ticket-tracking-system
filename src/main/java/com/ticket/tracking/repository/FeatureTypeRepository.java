package com.ticket.tracking.repository;

import com.ticket.tracking.entity.FeatureType;
import com.ticket.tracking.entity.ticket.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeatureTypeRepository extends MongoRepository<FeatureType, String> {
    Optional<FeatureType> findById(String id);

    @Query("{'ticketType': {'$regex': ?0, '$options': 'i'}, '_id': ?1}")
    FeatureType findByTicketTypeByIdLikeIgnoreCase(String ticketType, String id);


}
