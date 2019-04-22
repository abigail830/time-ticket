package com.github.abigail830.timeticket.integration;

import com.github.abigail830.timeticket.IntegrationTestBase;
import com.github.abigail830.timeticket.api.request.CreateUserRequest;
import com.github.abigail830.timeticket.domain.user.User;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.Assert.*;

/**
 * Demo for testing controller with rest-assured
 */
//@Ignore
public class UserIntegrationTest extends IntegrationTestBase {

    @Test
    public void should_get_all_user_if_exist() {
        prepareUserData(Arrays.asList("OPENID_1", "OPENID_2"));

        final List<Object> openIdList = when()
                .get("/users/all")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .jsonPath().getList("openId");

        assertTrue(openIdList.contains("OPENID_1"));
        assertTrue(openIdList.contains("OPENID_2"));

        cleanUpUserData(Arrays.asList("OPENID_1", "OPENID_2"));
    }


    @Test
    public void should_get_user_by_openId_if_exist() {
        prepareUserData(Arrays.asList("OPENID_3"));

        final User result =
                given()
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .queryParam("openId", "OPENID_3")
                        .when()
                        .get("/users")
                        .then()
                        .statusCode(HttpStatus.OK.value())
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .extract()
                        .as(User.class);

        assertNotNull(result);
        assertEquals("OPENID_3", result.getOpenId());

        cleanUpUserData(Arrays.asList("OPENID_3"));
    }

    @Test
    public void should_get_404_if_openid_not_exist() {

        final User result =
                given()
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .queryParam("openId", "NOT_EXIST")
                        .when()
                        .get("/users")
                        .then()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .extract()
                        .as(User.class);

        assertNotNull(result);
        assertNull(result.getId());

    }

    @Test
    public void should_add_new_user_when_post() {

        CreateUserRequest request = CreateUserRequest.builder()
                .avatarUrl("URL").openId("OPENID_4").build();

        User result = given()
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(request)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .extract()
                .as(User.class);

        assertNotNull(result);
        assertNotNull(result.getId());

        cleanUpUserData(Arrays.asList("OPENID_4"));
    }

    private void prepareUserData(List<String> openIds) {
        openIds.stream().forEach(id -> userRepository.addUser(User.builder().openId(id).build()));
    }

    private void cleanUpUserData(List<String> openIds) {
        openIds.stream().forEach(id -> userRepository.deleteUserByOpenId(id));
    }
}