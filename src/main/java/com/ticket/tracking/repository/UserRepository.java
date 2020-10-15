package com.ticket.tracking.repository;

import com.ticket.tracking.entity.User;
import com.ticket.tracking.entity.UserResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);

    @Query("{'_id': ?0}")
    User findUserById(String id);

    @Query("{'userRole': {'$regex': ?0, '$options': 'i'}}")
    List<User> findUserByUserRole(String userRole);
}
