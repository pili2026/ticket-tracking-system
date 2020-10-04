package com.ticket.tracking;

import com.ticket.tracking.entity.ticket.Ticket;
import com.ticket.tracking.repository.TickRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TicketTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TickRepository tickRepository;

    private HttpHeaders httpHeaders;

    @Before
    public void init() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
    }

    @After
    public void clear() {
        tickRepository.deleteAll();
    }

    @Test
    public void testCreateProduct() throws Exception {
        JSONObject request = new JSONObject();
        request.put("summary", "Harry Potter");
        request.put("priority", 450);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .post("/tickets")
                        .headers(httpHeaders)
                        .content(request.toString());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").hasJsonPath())
                .andExpect(jsonPath("$.summary").value(request.getString("summary")))
                .andExpect(jsonPath("$.priority").value(request.getInt("priority")))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"));
    }

    @Test
    public void testGetProduct() throws Exception {
        Ticket ticket = createTicket("Economics", 450);
        tickRepository.insert(ticket);

        mockMvc.perform(get("/tickets/" + ticket.getId())
                .headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticket.getId()))
                .andExpect(jsonPath("$.summary").value(ticket.getSummary()))
                .andExpect(jsonPath("$.priority").value(ticket.getCreateDate()));
    }

    @Test
    public void testReplaceTicket() throws Exception {
        Ticket ticket = createTicket("Economics", 450);
        tickRepository.insert(ticket);

        JSONObject request = new JSONObject();
        request.put("summary", "Macroeconomics");
        request.put("priority", 550);

        mockMvc.perform(put("/tickets/" + ticket.getId())
                .headers(httpHeaders)
                .content(request.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticket.getId()))
                .andExpect(jsonPath("$.summary").value(request.getString("summary")))
                .andExpect(jsonPath("$.priority").value(request.getInt("priority")));
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteTicket() throws Exception {
        Ticket ticket = createTicket("Economics", 450);
        tickRepository.insert(ticket);

        mockMvc.perform(delete("/tickets/" + ticket.getId())
                .headers(httpHeaders))
                .andExpect(status().isNoContent());

        tickRepository.findById(ticket.getId())
                .orElseThrow(RuntimeException::new);
    }

    @Test
    public void testSearchTicketSortByPriorityAsc() throws Exception {
        Ticket p1 = createTicket("Operation Management", 350);
        Ticket p2 = createTicket("Marketing Management", 200);
        Ticket p3 = createTicket("Human Resource Management", 420);
        Ticket p4 = createTicket("Finance Management", 400);
        Ticket p5 = createTicket("Enterprise Resource Planning", 440);
        tickRepository.insert(Arrays.asList(p1, p2, p3, p4, p5));

        MvcResult result = mockMvc.perform(get("/tickets")
                .headers(httpHeaders)
                .param("keyword", "Manage")
                .param("orderBy", "priority")
                .param("sortRule", "asc"))
                .andReturn();

        MockHttpServletResponse mockHttpResponse = result.getResponse();
        String responseJSONStr = mockHttpResponse.getContentAsString();
        JSONArray productJSONArray = new JSONArray(responseJSONStr);

        List<String> productIds = new ArrayList<>();
        for (int i = 0; i < productJSONArray.length(); i++) {
            JSONObject productJSON = productJSONArray.getJSONObject(i);
            productIds.add(productJSON.getString("id"));
        }

        Assert.assertEquals(4, productIds.size());
        Assert.assertEquals(p2.getId(), productIds.get(0));
        Assert.assertEquals(p1.getId(), productIds.get(1));
        Assert.assertEquals(p4.getId(), productIds.get(2));
        Assert.assertEquals(p3.getId(), productIds.get(3));

        Assert.assertEquals(HttpStatus.OK.value(), mockHttpResponse.getStatus());
        Assert.assertEquals("application/json;charset=UTF-8", mockHttpResponse.getContentType());
    }

    private Ticket createTicket(String summary, int create_date) {
        Ticket ticket = new Ticket();
        ticket.setSummary(summary);
        ticket.setCreateDate(create_date);

        return ticket;
    }
}
