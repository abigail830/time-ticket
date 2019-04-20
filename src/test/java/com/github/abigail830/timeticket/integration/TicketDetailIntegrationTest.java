package com.github.abigail830.timeticket.integration;

import com.github.abigail830.timeticket.domain.ticket.Ticket;
import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import com.github.abigail830.timeticket.infrastructure.repository.RawTicketIndexRepository;
import com.github.abigail830.timeticket.infrastructure.repository.RawTicketRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Demo for testing controller with spring MockMvc
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.datasource.url=jdbc:h2:mem:TicketDetailIntegrationTest;DB_CLOSE_DELAY=-1;MODE=MYSQL"})
public class TicketDetailIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RawTicketIndexRepository rawTicketIndexRepository;

    @Autowired
    RawTicketRepository rawTicketRepository;

    @Test
    public void test_get_all_tickets_detail() throws Exception {
        //prepare data
        rawTicketIndexRepository.addTicketIndex(new TicketIndex("OwnerOpenId", "AssignRole"));
        rawTicketRepository.addTicketToIndex(new Ticket(1, "event", 1200L));

        mockMvc.perform(
                get("/tickets/detail/all")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].event").value("event"));

    }

}