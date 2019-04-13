package com.github.abigail830.timeticket.infrastructure.repository.mapper;

import com.github.abigail830.timeticket.domain.ticket.TicketIndex;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketIndexRowMapper implements RowMapper<TicketIndex> {

    @Override
    public TicketIndex mapRow(ResultSet resultSet, int i) throws SQLException {
        if (StringUtils.isNotBlank(resultSet.getString("index_id"))) {
            return new TicketIndex(resultSet.getInt("index_id"),
                    resultSet.getString("owner_open_id"),
                    resultSet.getString("assignee_role"),
                    resultSet.getTimestamp("index_create_time"),
                    resultSet.getLong("sum_duration")
            );

        } else {
            return null;
        }
    }
}
