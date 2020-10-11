package com.ticket.tracking.repository;

import com.ticket.tracking.entity.TicketType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketTypeRepository extends MongoRepository<TicketType, String> {
    Optional<TicketType> findById(String id);

    @Query("{'ticketType': {'$regex': ?0, '$options': 'i'}, '_id': ?1}")
    TicketType findByTicketTypeByIdLikeIgnoreCase(String ticketType, String id);

    @Query("{'ticketType': {'$regex': ?0, '$options': 'i'}}")
    List<TicketType> findByTicketTypeLikeIgnoreCase(String ticketType);


}
