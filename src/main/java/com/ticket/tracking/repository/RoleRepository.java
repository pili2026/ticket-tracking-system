package com.ticket.tracking.repository;

import com.ticket.tracking.entity.Role;
import com.ticket.tracking.entity.Ticket;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    @Query("{'$and': [{'createDate': {'$gte': ?0, '$lte': ?1}}, {'roleType': {'$regex': ?2, '$options': 'i'}}]}")
    List<Role> findByCreateDateToBetweenAndSummaryLikeIgnoreCase(int createDateFrom, int createDateTo, String roleType, Sort sort);

    @Query("{'roleType': {'$regex': ?0, '$options': 'i'}, '_id': ?1}")
    Role findByRoleTypeByIdLikeIgnoreCase(String roleType, String id);

    @Query("{'roleType': {'$regex': ?0, '$options': 'i'}}")
    List<Role> findByRoleTypeLikeIgnoreCase(String roleType);
}
