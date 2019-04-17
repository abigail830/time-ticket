package com.github.abigail830.timeticket.infrastructure.repository;

import com.github.abigail830.timeticket.domain.ticket.Ticket;
import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import com.github.abigail830.timeticket.infrastructure.repository.po.ComplexTicketIndexPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ComplexTicketIndexRepositoryImpl implements ComplexTicketIndexRepository {


    private final JdbcTemplate jdbcTemplate;

    private RowMapper<ComplexTicketIndexPO> rowMapper = new BeanPropertyRowMapper<>(ComplexTicketIndexPO.class);

    @Autowired
    public ComplexTicketIndexRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TicketIndex getTicketDetailListByIndexId(Integer ticketIndexId) {
        final List<ComplexTicketIndexPO> result = jdbcTemplate.query(
                "select ticket_index_tbl.ID as index_id, " +
                        "ticket_index_tbl.owner_open_id as owner_open_id, " +
                        "ticket_index_tbl.assignee_open_id as assignee_open_id, " +
                        "ticket_index_tbl.assignee_role as assignee_role, " +
                        "ticket_index_tbl.sum_duration as sum_duration, " +
                        "ticket_index_tbl.create_time as index_create_time, " +
                        "ticket_tbl.ID as ticket_id, " +
                        "ticket_tbl.ticket_index_id as ticket_index_id, " +
                        "ticket_tbl.event as event, " +
                        "ticket_tbl.event_status as event_status, " +
                        "ticket_tbl.duration as duration, " +
                        "ticket_tbl.create_time as ticket_create_time, " +
                        "ticket_tbl.last_update_time as ticket_last_update_time, " +
                        "user_tbl.ID as user_id, " +
                        "user_tbl.open_id as open_id, " +
                        "user_tbl.avatar_url as avatar_url " +
                        "from ticket_index_tbl " +
                        "left join ticket_tbl on ticket_index_tbl.ID = ticket_tbl.ticket_index_id " +
                        "left join user_tbl on ticket_index_tbl.assignee_open_id = user_tbl.open_id " +
                        "where  ticket_index_tbl.ID = ?",
                rowMapper, ticketIndexId);


        final List<Ticket> tickets = result.stream().map(ComplexTicketIndexPO::toTicketDomain).collect(Collectors.toList());

        final Optional<TicketIndex> ticketIndex = result.stream().map(ComplexTicketIndexPO::toRawTicketIndexDomain).findFirst();
        if (ticketIndex.isPresent()) {
            final TicketIndex ticketIndex1 = ticketIndex.get();
            ticketIndex1.addTickets(tickets);
            log.info("TicketIndex1 is {}", ticketIndex1);
            return ticketIndex1;
        }

        return null;
    }
}
