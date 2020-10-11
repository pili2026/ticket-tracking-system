package com.ticket.tracking.service;

import com.ticket.tracking.entity.BugType;
import com.ticket.tracking.entity.FeatureType;
import com.ticket.tracking.entity.TestCaseType;
import com.ticket.tracking.function.TimeFunc;
import com.ticket.tracking.repository.BugTypeRepository;
import com.ticket.tracking.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QaTypeService {

    @Autowired
    private BugTypeRepository bugTypeRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

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

    public List<TestCaseType> getTestCaseTypeTickets() {
        List<TestCaseType> bugTypes = testCaseRepository.findByTicketTypeLikeIgnoreCase("TestCase");
        return new ArrayList<>(bugTypes);
    }

    public TestCaseType getTestCaseTicket(String id) {
        Optional<TestCaseType> testCaseType = testCaseRepository.findById(id);
        TestCaseType testCase = null;

        if (testCaseType.isPresent()) {
            testCase = testCaseType.get();
        } else {
            throw new RuntimeException("Feature ticket not found for id ::" + id);
        }
        return testCase;
    }

    public void createBugTicket(BugType bugType, String name) {
        // epoch
        TimeFunc timeFunc = new TimeFunc();

        bugType.setSummary(bugType.getSummary());
        bugType.setDescription(bugType.getDescription());
        bugType.setPriority(bugType.getPriority());
        bugType.setSeverity(bugType.getSeverity());
        bugType.setTicketStatus(bugType.getTicketStatus());
        bugType.setTicketType(bugType.getTicketType());
        bugType.setCreateDate(timeFunc.localDateTime());
        bugType.setUpdatedDate(timeFunc.localDateTime());
        bugType.setAssignee(bugType.getAssignee());
        bugType.setReporter(name);
        bugTypeRepository.save(bugType);

    }

    public void updateBugTicket(BugType bugType, String name) {
        TimeFunc timeFunc = new TimeFunc();
        BugType oldBug = bugTypeRepository.findByTicketTypeByIdLikeIgnoreCase(bugType.getTicketType(), bugType.getId());
        bugType.setSummary(bugType.getSummary());
        bugType.setDescription(bugType.getDescription());
        bugType.setPriority(oldBug.getPriority());
        bugType.setSeverity(oldBug.getSeverity());
        bugType.setTicketStatus(bugType.getTicketStatus());
        bugType.setTicketType(oldBug.getTicketType());
        bugType.setCreateDate(oldBug.getCreateDate());
        bugType.setUpdatedDate(timeFunc.localDateTime());
        bugType.setReporter(oldBug.getReporter());
        bugType.setAssignee(oldBug.getAssignee());
        bugType.setReporter(name);
        bugTypeRepository.save(bugType);

    }

    public void deleteTicket(String id) {
        bugTypeRepository.deleteById(id);
    }
}
