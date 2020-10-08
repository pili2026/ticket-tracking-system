package com.ticket.tracking.repository;

import com.ticket.tracking.entity.FeatureType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeatureTypeRepository extends MongoRepository<FeatureType, String> {
    Optional<FeatureType> findById(String id);

    List<FeatureType> findAll();
}
