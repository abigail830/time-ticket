package com.github.abigail830.timeticket.domain;

import com.github.abigail830.timeticket.infrastructure.UserRepositoryImpl;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserDomainTest {

    private static final String JDBC_URL = "jdbc:h2:mem:UserDomainTest;DB_CLOSE_DELAY=-1;MODE=MYSQL";
    static JdbcTemplate jdbcTemplate;
    static UserRepositoryImpl userRepository;

    @BeforeClass
    public static void setUp() {
        final JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(JDBC_URL);
        Flyway flyway = Flyway.configure().dataSource(jdbcDataSource).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(jdbcDataSource);

        userRepository = new UserRepositoryImpl();
        userRepository.setJdbcTemplate(jdbcTemplate);
    }

    @AfterClass
    public static void tearDown() {
        jdbcTemplate.update("DELETE FROM user_tbl");
    }

    @Test
    public void should_add_user_with_open_id() {
        //given
        String OPENID = "OPENID1";
        //when
        final UserDomain userDomain = new UserDomain(OPENID, userRepository);
        //then
        assertTrue(userDomain.isExistUser(OPENID));
    }

    @Test
    public void should_update_user() {
        //given
        String OPENID = "OPENID2";
        final UserDomain userDomain = new UserDomain(OPENID, userRepository);
        final User updatedUser = User.builder().openId(OPENID).avatarUrl("URL")
                .city("GZ").country("CN").province("GD")
                .gender("M").lang("CN").nickName("Sara").build();
        //when
        userDomain.updateUser(updatedUser);
        //then
        final User userByOpenId = userDomain.getUserByOpenId(OPENID);
        assertEquals(updatedUser.getAvatarUrl(), userByOpenId.getAvatarUrl());
        assertEquals(updatedUser.getCity(), userByOpenId.getCity());
    }
}