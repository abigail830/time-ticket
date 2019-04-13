package com.github.abigail830.timeticket.infrastructure.ticket.mapper;

import com.github.abigail830.timeticket.domain.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        if (StringUtils.isNotBlank(resultSet.getString("open_id"))) {
            return User.builder()
                    .openId(resultSet.getString("open_id"))
                    .avatarUrl(resultSet.getString("avatar_url"))
                    .build();
        } else {
            return null;
        }
    }
}
