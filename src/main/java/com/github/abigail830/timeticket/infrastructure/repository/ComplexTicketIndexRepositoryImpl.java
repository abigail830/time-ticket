package com.github.abigail830.timeticket.infrastructure.repository;

import com.github.abigail830.timeticket.domain.ticket.Ticket;
import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import com.github.abigail830.timeticket.domain.user.User;
import com.github.abigail830.timeticket.infrastructure.repository.mapper.TicketIndexRowMapper;
import com.github.abigail830.timeticket.infrastructure.repository.mapper.TicketRowMapper;
import com.github.abigail830.timeticket.infrastructure.repository.mapper.UserRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Slf4j
public class ComplexTicketIndexRepositoryImpl implements ComplexTicketIndexRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<TicketIndex> ticketIndexRowMapper = new TicketIndexRowMapper();
    private RowMapper<Ticket> ticketRowMapper = new TicketRowMapper();
    private RowMapper<User> userRowMapper = new UserRowMapper();

    @Override
    public TicketIndex getTicketDetailListByIndexId(Integer ticketIndexId) {
        TicketIndex ticketIndex = jdbcTemplate.query(
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

                new ResultSetExtractor<TicketIndex>() {
                    @Override
                    public TicketIndex extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                        TicketIndex ticketIndex = null;
                        int row = 0;
                        while (resultSet.next()) {
                            if (ticketIndex == null) {
                                ticketIndex = ticketIndexRowMapper.mapRow(resultSet, row);
                            }

                            User user = userRowMapper.mapRow(resultSet, row);
                            if (user != null) {
                                ticketIndex.setAssignee(user);
                                log.debug("User {}", user);
                            }

                            Ticket ticket = ticketRowMapper.mapRow(resultSet, row);
                            if (ticket != null) {
                                log.debug("Ticket {}", ticket);
                                ticketIndex.addTicket(ticket);
                            }

                            row++;
                        }
                        return ticketIndex;
                    }
                }, ticketIndexId);


        return ticketIndex;
    }
}
