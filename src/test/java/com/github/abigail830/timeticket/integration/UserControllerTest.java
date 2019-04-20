package com.github.abigail830.timeticket.integration;

import com.github.abigail830.timeticket.IntegrationBase;
import com.github.abigail830.timeticket.domain.user.User;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserControllerTest extends IntegrationBase {

    @Test
    public void should_get_all_user_if_exist() {
        userRepository.addUser(User.builder().openId("OPENID_1").build());
        userRepository.addUser(User.builder().openId("OPENID_2").build());

        final List<Object> openIdList = when()
                .get("/users/all")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .jsonPath().getList("openId");

        assertEquals(2, openIdList.size());
        assertTrue(openIdList.contains("OPENID_1"));
        assertTrue(openIdList.contains("OPENID_2"));
    }
}