package com.ticket.tracking.service;

import com.ticket.tracking.entity.FeatureType;
import com.ticket.tracking.entity.ticket.Ticket;
import com.ticket.tracking.repository.FeatureTypeRepository;
import com.ticket.tracking.repository.TickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeatureService {

    @Autowired
    private FeatureTypeRepository repository;

    @Autowired
    private TickRepository tickRepository;

    public void createFeatureTicket(FeatureType featureType) {
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        featureType.setSummary(featureType.getSummary());
        featureType.setDescription(featureType.getDescription());
        featureType.setPriority(featureType.getPriority());
        featureType.setSeverity(featureType.getSeverity());
        featureType.setTicketStatus(featureType.getTicketStatus());
        featureType.setTicketType("Feature");
        featureType.setCreateDate(timeStampMillis);
        featureType.setAssignee(featureType.getAssignee());
        featureType.setReporter("Jimmy");
        repository.save(featureType);

    }

    public List<FeatureType> findFeatureTicket() {
        List<FeatureType> featureTypes = repository.findAll();
        return new ArrayList<>(featureTypes);
    }

}
