package com.ticket.tracking.repository;

import com.ticket.tracking.entity.role.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);

}
