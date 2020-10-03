package com.ticket.tracking.repository;

import com.ticket.tracking.entity.Ticket;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TickRepository  extends MongoRepository<Ticket, String> {

    @Query("{'$and': [{'createDate': {'$gte': ?0, '$lte': ?1}}, {'summary': {'$regex': ?2, '$options': 'i'}}]}")
    List<Ticket> findByCreateDateToBetweenAndSummaryLikeIgnoreCase(int createDateFrom, int createDateTo, String summary, Sort sort);

    @Query("{'priority': {'$gte': ?0, '$lte': ?1}}")
    List<Ticket> findByPriorityBetween(int from, int to);

    @Query("{'summary': {'$regex': ?0, '$options': 'i'}}")
    List<Ticket> findBySummaryLikeIgnoreCase(String summary);

    @Query("{'$and': [{'createDate': {'$gte': ?0, '$lte': ?1}}, {'summary': {'$regex': ?2, '$options': 'i'}}]}")
    List<Ticket> findByCreateDateBetweenAndSummaryLikeIgnoreCase(int createDateFrom, int createDateTo, String summary);

    @Query(value = "{'_id': {'$in': ?0}}", count = true)
    int countByIdIn(List<String> ids);

    @Query(value = "{'_id': {'$in': ?0}}", exists = true)
    boolean existsByIdIn(List<String> ids);

    @Query(delete = true)
    void deleteByIdIn(List<String> ids);

    @Query(sort = "{'summary': 1, 'price': -1}")
    List<Ticket> findByIdInOrderBySummaryAscPriorityDesc(List<String> ids);

}
