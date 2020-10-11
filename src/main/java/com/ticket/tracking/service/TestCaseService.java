package com.ticket.tracking.service;

import com.ticket.tracking.entity.BugType;
import com.ticket.tracking.entity.FeatureType;
import com.ticket.tracking.entity.TestCaseType;
import com.ticket.tracking.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestCaseService {

    @Autowired
    private TestCaseRepository testCaseRepository;

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
}
