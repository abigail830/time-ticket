package com.github.abigail830.timeticket.infrastructure.repository;

import com.github.abigail830.timeticket.domain.user.User;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Setter
@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);

    @Override
    public void addUserByOpenId(String openId) {
        log.info("Going to insert client into DB with openId: {}", openId);
        jdbcTemplate.update("INSERT ignore INTO user_tbl (open_id) VALUES (?)", openId);
    }

    public User updateUser(User user) {
        log.info("Going to update client into DB: {}", user);
        jdbcTemplate.update(
                "UPDATE user_tbl set gender=?, nick_name=?, city=?, country=?, province=?, lang=?, avatar_url=? where open_id=?",
                user.getGender(),
                user.getNickName(),
                user.getCity(),
                user.getCountry(),
                user.getProvince(),
                user.getLang(),
                user.getAvatarUrl(),
                user.getOpenId()
        );
        return user;
    }

    public Optional<User> getUserByOpenId(String openId) {
        List<User> users = jdbcTemplate.query("SELECT * FROM user_tbl WHERE open_id = ?", rowMapper, openId);
        return users.stream().findFirst();
    }

    public Optional<User> getUserById(String id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM user_tbl WHERE id = ?", rowMapper, id);
        return users.stream().findFirst();
    }

    public List<User> getAllUsers() {
        return jdbcTemplate.query("SELECT * from user_tbl", rowMapper);
    }


}