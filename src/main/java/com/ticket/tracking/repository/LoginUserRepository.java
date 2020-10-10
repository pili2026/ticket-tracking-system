package com.ticket.tracking.repository;

import com.ticket.tracking.entity.role.LoginUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface LoginUserRepository extends MongoRepository<LoginUser, String> {

    LoginUser findByEmail(String email);

    @Query("{'userRole': {'$regex': ?0, '$options': 'i'}}")
    List<LoginUser> findUserByUserRole(String userRole);
}
