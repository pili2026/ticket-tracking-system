package com.ticket.tracking.service;

import com.ticket.tracking.convert.TicketConverter;
import com.ticket.tracking.entity.FeatureType;
import com.ticket.tracking.repository.FeatureTypeRepository;
import com.ticket.tracking.repository.TickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeatureService {

    @Autowired
    private FeatureTypeRepository repository;

    public void createFeatureTicket(FeatureType featureType) {
        featureType.setSummary("Firewall");
        featureType.setDescription("New Feature");
        featureType.setPriority("High");
        featureType.setSeverity("Critical");
        featureType.setTicketStatus("New");
        featureType.setTicketType("Feature");
        featureType.setCreateDate(1602088142);
        featureType.setExpectedDate(1602088142);
        featureType.setResolveDate(1602088142);
        featureType.setAssignee("Jeremy");
        featureType.setReporter("Jimmy");
        repository.save(featureType);

    }

    public List<FeatureType> findFeatureTicket() {
        List<FeatureType> featureTypes = repository.findAll();
        return new ArrayList<>(featureTypes);
    }

}
