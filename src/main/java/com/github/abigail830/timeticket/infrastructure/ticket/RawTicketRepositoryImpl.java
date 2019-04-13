package com.github.abigail830.timeticket.infrastructure.ticket;

import com.github.abigail830.timeticket.domain.ticket.Ticket;
import com.github.abigail830.timeticket.infrastructure.InfraException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class RawTicketRepositoryImpl implements RawTicketRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Ticket> rawTicketRowMapper = new BeanPropertyRowMapper<>(Ticket.class);


    @Override
    public List<Ticket> getAllTickets() {
        final List<Ticket> ticketList = jdbcTemplate.query("SELECT * FROM ticket_tbl", rawTicketRowMapper);
        return ticketList;
    }

    @Override
    public Optional<Ticket> getTicketById(Integer ticketId) {
        final List<Ticket> tickets = jdbcTemplate.query("SELECT * FROM ticket_tbl WHERE ID = ?",
                rawTicketRowMapper, ticketId);
        return tickets.stream().findFirst();
    }

    @Override
    public void addTicketToIndex(Ticket ticket) {
        try {
            jdbcTemplate.update(
                    "INSERT INTO ticket_tbl (event, event_status, duration, ticket_index_id) VALUES (?,?,?,?)",
                    ticket.getEvent(),
                    ticket.getEventStatus(),
                    ticket.getDuration(),
                    ticket.getTicketIndexId()
            );
            log.info("Created Ticket {}", ticket.toString());
        } catch (DataIntegrityViolationException ex) {
            log.warn("DataIntegrityViolationException happened. \n {}", ex);
            throw new InfraException("DataIntegrityViolationException: Ticket Index not exist", ex.getCause());
        } catch (Exception e) {
            log.warn("Generic exception happened. \n {}", e);
            throw new InfraException("GenericException", e.getCause());
        }
    }

    @Override
    public void updateTicket(Ticket ticket) {
        jdbcTemplate.update("UPDATE ticket_tbl set event=?, duration=?  where ID=?",
                ticket.getEvent(), ticket.getDuration(),
                ticket.getId());
        log.info("Updated Ticket with new content {}", ticket);
    }

    @Override
    public void removeTicket(Integer ticketId) {
        jdbcTemplate.update("DELETE from ticket_tbl where ID=?", ticketId);
        log.info("Deleted Ticket with id = {}", ticketId);
    }

    @Override
    public void removeTicketByIndexId(Integer ticketIndexId) {
        jdbcTemplate.update("DELETE from ticket_tbl where ticket_index_id=?", ticketIndexId);
        log.info("Deleted Ticket with ticket_index_id = {}", ticketIndexId);
    }

}
