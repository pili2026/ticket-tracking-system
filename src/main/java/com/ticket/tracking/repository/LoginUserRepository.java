package com.ticket.tracking.repository;

import com.ticket.tracking.entity.role.LoginUser;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface LoginUserRepository extends MongoRepository<LoginUser, String> {

    LoginUser findByEmail(String email);
}
