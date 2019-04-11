package com.github.abigail830.timeticket.infrastructure.ticket;

import com.github.abigail830.timeticket.domain.User;
import com.github.abigail830.timeticket.domain.ticket.Ticket;
import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import com.github.abigail830.timeticket.domain.ticket.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
public class TicketRepositoryImpl implements TicketRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<TicketIndex> ticketIndexRowMapper = new BeanPropertyRowMapper<>(TicketIndex.class);

    private RowMapper<Ticket> ticketRowMapper = new TicketRowMapper();

    private RowMapper<User> userRowMapper = new UserRowMapper();


    @Override
    public void addTicketIndex(TicketIndex ticketIndex) {
        jdbcTemplate.update(
                "INSERT INTO ticket_index_tbl (owner_open_id, assignee_role, sum_duration) VALUES (?,?,?)",
                ticketIndex.getOwnerOpenId(),
                ticketIndex.getAssigneeRole(),
                ticketIndex.getSumDuration()
        );
        log.info("Created {}", ticketIndex.toString());
    }

    @Override
    public void updateAssigneeRoleOfTicketIndex(Integer id, String assigneeRole) {
        jdbcTemplate.update("UPDATE ticket_index_tbl set assignee_role=?  where ID=?", assigneeRole, id);
        log.info("Updated TicketIndex {} with new assigneeRole", id, assigneeRole);
    }

    @Override
    public void removeTicketIndex(Integer ticketIndexId) {
        jdbcTemplate.update("DELETE from ticket_index_tbl where ID=?", ticketIndexId);
        log.info("Deleted TicketIndex with id {}", ticketIndexId);

    }

    @Override
    public List<TicketIndex> getTicketIndexByOwnerOpenIdOrderBySumDuration(String ownerOpenId) {
        List<TicketIndex> ticketIndexList = jdbcTemplate.query(
                "SELECT * FROM ticket_index_tbl WHERE owner_open_id = ?", ticketIndexRowMapper, ownerOpenId);
        return ticketIndexList;
    }

    @Override
    public void addTicketToIndex(Integer ticketIndexId, Ticket ticket) {
        jdbcTemplate.update(
                "INSERT INTO ticket_tbl (event, event_status, duration, ticket_index_id) VALUES (?,?,?,?)",
                ticket.getEvent(),
                ticket.getEventStatus(),
                ticket.getDuration(),
                ticket.getTicketIndexId()
        );
        log.info("Created Ticket {}", ticket.toString());
    }

    @Override
    public void updateTicket(Integer ticketId, Ticket ticket) {

    }

    @Override
    public void removeTicket(Integer ticketId) {

    }

    @Override
    public List<Ticket> getAllTickets() {
        final List<Ticket> ticketList = jdbcTemplate.query("SELECT * FROM ticket_index_tbl", ticketRowMapper);
        return ticketList;
    }

    @Override
    public TicketIndex getTicketDetailListByIndexId(Integer timeIndexId) {
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
                }, timeIndexId);


        return ticketIndex;
    }


}
