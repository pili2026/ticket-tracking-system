package com.ticket.tracking.service;

import com.ticket.tracking.entity.FeatureType;
import com.ticket.tracking.entity.role.LoginUser;
import com.ticket.tracking.entity.ticket.Ticket;
import com.ticket.tracking.repository.FeatureTypeRepository;
import com.ticket.tracking.repository.LoginUserRepository;
import com.ticket.tracking.repository.TickRepository;
import com.ticket.tracking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeatureService {

    @Autowired
    private FeatureTypeRepository repository;

    public void createFeatureTicket(FeatureType featureType, String name) {
        // epoch
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        System.out.println(featureType.getTicketStatus());
        featureType.setSummary(featureType.getSummary());
        featureType.setDescription(featureType.getDescription());
        featureType.setPriority(featureType.getPriority());
        featureType.setSeverity(featureType.getSeverity());
        featureType.setTicketStatus(featureType.getTicketStatus());
        featureType.setTicketType("Feature");
        featureType.setCreateDate(timeStampMillis);
        featureType.setAssignee(featureType.getAssignee());
        featureType.setReporter(name);
        repository.save(featureType);

    }

    public List<FeatureType> findFeatureTickets() {
        List<FeatureType> featureTypes = repository.findAll();
        return new ArrayList<>(featureTypes);
    }

    public FeatureType getTicketResponsesByTypeId(String id) {
        return repository.findByTicketTypeByIdLikeIgnoreCase("Feature", id);
    }

    public void deleteTicket(String id) {
        repository.deleteById(id);
    }

    public FeatureType getFeatureTicket(String id) {
        Optional<FeatureType> featureType = repository.findById(id);
        FeatureType feature = null;

        if (featureType.isPresent()) {
            feature = featureType.get();
        } else {
            throw new RuntimeException("Feature ticket not found for id ::" + id);
        }
        return feature;
    }

    public void replaceTicketTypeById(String type, String id, FeatureType featureType) {
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        FeatureType oldTicket = repository.findByTicketTypeByIdLikeIgnoreCase(type, id);
        System.out.println(featureType.getSeverity());
        FeatureType newTicket = new FeatureType();
        newTicket.setId(oldTicket.getId());
        newTicket.setSummary(featureType.getSummary());
        newTicket.setDescription(featureType.getDescription());
        newTicket.setPriority(featureType.getPriority());
        newTicket.setSeverity(featureType.getSeverity());
        newTicket.setTicketStatus(featureType.getTicketStatus());
        newTicket.setTicketType("Feature");
        newTicket.setCreateDate(featureType.getCreateDate());
        newTicket.setAssignee(featureType.getAssignee());
        newTicket.setReporter(oldTicket.getReporter());
        newTicket.setUpdatedDate(timeStampMillis);
        repository.save(newTicket);

    }

}
