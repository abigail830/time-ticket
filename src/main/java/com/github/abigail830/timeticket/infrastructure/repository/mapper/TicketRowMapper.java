package com.github.abigail830.timeticket.infrastructure.repository.mapper;

import com.github.abigail830.timeticket.domain.ticket.Ticket;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketRowMapper implements RowMapper<Ticket> {

    @Override
    public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
        if (StringUtils.isNotBlank(resultSet.getString("ticket_id"))) {
            return new Ticket(resultSet.getInt("ticket_id"),
                    resultSet.getInt("ticket_index_id"),
                    resultSet.getTimestamp("ticket_create_time"),
                    resultSet.getTimestamp("ticket_last_update_time"),
                    resultSet.getString("event_status"),
                    resultSet.getString("event"),
                    resultSet.getLong("duration")
            );

        } else {
            return null;
        }
    }
}
