package com.github.abigail830.timeticket.infrastructure.ticket;

import com.github.abigail830.timeticket.domain.ticket.Ticket;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketRowMapper implements RowMapper<Ticket> {

    @Override
    public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
        if (StringUtils.isNotBlank(resultSet.getString("ticket_id"))) {
            return Ticket.builder()
                    .id(resultSet.getInt("ticket_id"))
                    .ticketIndexId(resultSet.getInt("ticket_index_id"))
                    .event(resultSet.getString("event"))
                    .eventStatus(resultSet.getString("event_status"))
                    .duration(resultSet.getLong("duration"))
                    .createTime(resultSet.getTimestamp("ticket_create_time"))
                    .lastUpdateTime(resultSet.getTimestamp("ticket_last_update_time"))
                    .build();
        } else {
            return null;
        }
    }
}
