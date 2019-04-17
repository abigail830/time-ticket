package com.github.abigail830.timeticket.infrastructure.repository;

import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class ComplexTicketIndexRepositoryImplTest {

    protected static JdbcTemplate jdbcTemplate;
    private static JdbcDataSource ds;
    private static Flyway flyway;

    @BeforeClass
    static public void setUp() throws Exception {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:ComplexTicketIndexRepositoryImplTest;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(ds);
        RunScript.execute(ds.getConnection(),
                new StringReader("INSERT INTO user_tbl (open_id, avatar_url) VALUES ('OPEN_ID1','URL1')"));
        RunScript.execute(ds.getConnection(),
                new StringReader("INSERT INTO user_tbl (open_id, avatar_url) VALUES ('OPEN_ID2','URL2')"));
        RunScript.execute(ds.getConnection(),
                new StringReader("INSERT INTO ticket_index_tbl (owner_open_id, assignee_open_id,assignee_role) VALUES ('OPEN_ID1','OPEN_ID2','assigneeRole')"));
        RunScript.execute(ds.getConnection(),
                new StringReader("INSERT INTO ticket_tbl (ticket_index_id, event, event_status, duration) VALUES (1,'event','NEW', 1200)"));
    }


    @Test
    public void getTicketDetailListByIndexId() {

        ComplexTicketIndexRepositoryImpl repository = new ComplexTicketIndexRepositoryImpl(jdbcTemplate);
        UserRepositoryImpl userRepository = new UserRepositoryImpl(jdbcTemplate);
        RawTicketIndexRepositoryImpl ticketIndexRepository = new RawTicketIndexRepositoryImpl(jdbcTemplate);
        RawTicketRepositoryImpl ticketRepository = new RawTicketRepositoryImpl(jdbcTemplate);

        Assert.assertTrue(userRepository.getUserByOpenId("OPEN_ID2").isPresent());
        Assert.assertTrue(userRepository.getUserByOpenId("OPEN_ID1").isPresent());

        final TicketIndex ticketIndex = ticketIndexRepository.getTicketIndexByOwnerOpenIdOrderBySumDuration("OPEN_ID1")
                .stream().findFirst().orElse(null);
        Assert.assertNotNull(ticketIndex);

        final TicketIndex ticketDetailListByIndexId = repository.getTicketDetailListByIndexId(ticketIndex.getId());
        System.out.println(ticketDetailListByIndexId);
        assertEquals("assigneeRole", ticketDetailListByIndexId.getAssigneeRole());
        assertEquals("OPEN_ID2", ticketDetailListByIndexId.getAssignee().getOpenId());
        assertEquals("URL2", ticketDetailListByIndexId.getAssignee().getAvatarUrl());
        assertEquals(1, ticketDetailListByIndexId.getTicketList().size());
        assertEquals(1, ticketDetailListByIndexId.getTicketList().stream()
                .filter(ticket -> ticket.getEvent().equals("event")).count());


    }
}