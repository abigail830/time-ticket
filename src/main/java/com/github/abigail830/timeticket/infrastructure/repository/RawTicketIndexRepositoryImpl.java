package com.github.abigail830.timeticket.infrastructure.repository;

import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class RawTicketIndexRepositoryImpl implements RawTicketIndexRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<TicketIndex> rawTicketIndexRowMapper = new BeanPropertyRowMapper<>(TicketIndex.class);

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
                "SELECT * FROM ticket_index_tbl WHERE owner_open_id = ?", rawTicketIndexRowMapper, ownerOpenId);
        return ticketIndexList;
    }

    @Override
    public TicketIndex getTicketIndexByIndexId(Integer ticketIndexId) {
        final List<TicketIndex> query = jdbcTemplate.query("SELECT * from ticket_index_tbl where ID=?",
                rawTicketIndexRowMapper, ticketIndexId);
        if (query.isEmpty())
            return null;
        else
            return query.get(0);
    }

    @Override
    public void updateAssigneeAndSumDurationOfTicketIndex(TicketIndex updatedTicket) {
        jdbcTemplate.update("UPDATE ticket_index_tbl set assignee_open_id=?, sum_duration=?  where ID=?",
                updatedTicket.getAssignee().getOpenId(),
                updatedTicket.getSumDuration(),
                updatedTicket.getId());
        log.info("Updated TicketIndex with new content {}", updatedTicket);
    }
}
