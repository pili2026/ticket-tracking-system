package com.ticket.tracking.repository;

import com.ticket.tracking.entity.role.LoginUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoginUserRepository extends MongoRepository<LoginUser, String> {

    LoginUser findByEmail(String email);

    @Query("{'_id': ?0}")
    LoginUser findUserById(String id);

    @Query("{'userRole': {'$regex': ?0, '$options': 'i'}}")
    List<LoginUser> findUserByUserRole(String userRole);
}
