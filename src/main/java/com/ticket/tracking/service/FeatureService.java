package com.ticket.tracking.service;

import com.ticket.tracking.entity.FeatureType;
import com.ticket.tracking.entity.role.LoginUser;
import com.ticket.tracking.entity.ticket.Ticket;
import com.ticket.tracking.function.TimeFunc;
import com.ticket.tracking.repository.FeatureTypeRepository;
import com.ticket.tracking.repository.LoginUserRepository;
import com.ticket.tracking.repository.TickRepository;
import com.ticket.tracking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeatureService {

    @Autowired
    private FeatureTypeRepository repository;

    public void createFeatureTicket(FeatureType featureType, String name) {
        TimeFunc timeFunc = new TimeFunc();

        System.out.println(featureType.getTicketStatus());
        featureType.setSummary(featureType.getSummary());
        featureType.setDescription(featureType.getDescription());
        featureType.setPriority(featureType.getPriority());
        featureType.setSeverity(featureType.getSeverity());
        featureType.setTicketStatus(featureType.getTicketStatus());
        featureType.setTicketType("Feature");
        featureType.setCreateDate(timeFunc.localDateTime());
        featureType.setUpdatedDate(timeFunc.localDateTime());
        featureType.setAssignee(featureType.getAssignee());
        featureType.setReporter(name);
        repository.save(featureType);

    }

    public List<FeatureType> getFeatureTickets() {
        List<FeatureType> featureTypes = repository.findByTicketTypeLikeIgnoreCase("Feature");
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

    public void updateFeatureTicket(FeatureType featureType) {
        TimeFunc timeFunc = new TimeFunc();
        FeatureType oldFeature = repository.findByTicketTypeByIdLikeIgnoreCase(featureType.getTicketType(), featureType.getId());
        featureType.setSummary(featureType.getSummary());
        featureType.setDescription(featureType.getDescription());
        featureType.setPriority(oldFeature.getPriority());
        featureType.setSeverity(oldFeature.getSeverity());
        featureType.setTicketStatus(featureType.getTicketStatus());
        featureType.setTicketType(oldFeature.getTicketType());
        featureType.setCreateDate(oldFeature.getCreateDate());
        featureType.setAssignee(oldFeature.getAssignee());
        featureType.setReporter(oldFeature.getReporter());
        featureType.setUpdatedDate(timeFunc.localDateTime());
        repository.save(featureType);

    }

}
