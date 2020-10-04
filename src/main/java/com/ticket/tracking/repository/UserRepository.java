package com.ticket.tracking.repository;

import com.ticket.tracking.entity.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{'$and': [{'createDate': {'$gte': ?0, '$lte': ?1}}, {'userType': {'$regex': ?2, '$options': 'i'}}]}")
    List<User> findByCreateDateToBetweenAndUserTypeLikeIgnoreCase(int createDateFrom, int createDateTo, String userType, Sort sort);

    @Query("{'userType': {'$regex': ?0, '$options': 'i'}, 'account': ?1}")
    User findByUserTypeByAccountLikeIgnoreCase(String userType, String account);


    @Query("{'userType': {'$regex': ?0, '$options': 'i'}}")
    List<User> findByUserTypeLikeIgnoreCase(String userType);
}
