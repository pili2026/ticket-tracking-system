package com.ticket.tracking.repository;

import com.ticket.tracking.entity.ticket.Ticket;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TickRepository  extends MongoRepository<Ticket, String> {

    @Query("{'$and': [{'createDate': {'$gte': ?0, '$lte': ?1}}, {'summary': {'$regex': ?2, '$options': 'i'}}]}")
    List<Ticket> findByCreateDateToBetweenAndSummaryLikeIgnoreCase(int createDateFrom, int createDateTo, String summary, Sort sort);

    @Query("{'ticketType': {'$regex': ?0, '$options': 'i'}}")
    List<Ticket> findByTicketTypeLikeIgnoreCase(String ticketType);

    @Query("{'ticketType': {'$regex': ?0, '$options': 'i'}, '_id': ?1}")
    Ticket findByTicketTypeByIdLikeIgnoreCase(String ticketType, String id);

    @Query(sort = "{'summary': 1, 'price': -1}")
    List<Ticket> findByIdInOrderBySummaryAscPriorityDesc(List<String> ids);

}
