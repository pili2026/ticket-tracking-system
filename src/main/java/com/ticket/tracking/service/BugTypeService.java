package com.ticket.tracking.service;

import com.ticket.tracking.entity.BugType;
import com.ticket.tracking.entity.FeatureType;
import com.ticket.tracking.repository.BugTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BugTypeService {

    @Autowired
    private BugTypeRepository bugTypeRepository;

    public List<BugType> getBugTypeTickets() {
        List<BugType> bugTypes = bugTypeRepository.findByTicketTypeLikeIgnoreCase("Bug");
        return new ArrayList<>(bugTypes);
    }

    public BugType getBugTicket(String id) {
        Optional<BugType> bugType = bugTypeRepository.findById(id);
        BugType bug = null;

        if (bugType.isPresent()) {
            bug = bugType.get();
        } else {
            throw new RuntimeException("Feature ticket not found for id ::" + id);
        }
        return bug;
    }

    public void createBugTicket(BugType bugType, String name) {
        // epoch
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        LocalDate ld = Instant.ofEpochMilli(timeStampMillis)
                .atZone(ZoneId.systemDefault()).toLocalDate();

        bugType.setSummary(bugType.getSummary());
        bugType.setDescription(bugType.getDescription());
        bugType.setPriority(bugType.getPriority());
        bugType.setSeverity(bugType.getSeverity());
        bugType.setTicketStatus(bugType.getTicketStatus());
        bugType.setTicketType(bugType.getTicketType());
        bugType.setCreateDate(ld.toString());
        bugType.setUpdatedDate(ld.toString());
        bugType.setAssignee(bugType.getAssignee());
        bugType.setReporter(name);
        bugTypeRepository.save(bugType);

    }

    public void deleteTicket(String id) {
        bugTypeRepository.deleteById(id);
    }
}
