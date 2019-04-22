package com.github.abigail830.timeticket.integration;

import com.github.abigail830.timeticket.IntegrationTestBase;
import com.github.abigail830.timeticket.api.request.CreateUserRequest;
import com.github.abigail830.timeticket.domain.user.User;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.Assert.*;

/**
 * Demo for testing controller with rest-assured + DB unit
 */
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.datasource.url=jdbc:h2:mem:UserDBUnitIntegrationTest;DB_CLOSE_DELAY=-1;MODE=MYSQL"})
public class UserDBUnitIntegrationTest extends IntegrationTestBase {

    @Test
    @DatabaseSetup(value = "/dbunit/UserDBUnitIntegrationTest.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void should_get_all_user_if_exist() {
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

    @Test
    @DatabaseSetup(value = "/dbunit/UserDBUnitIntegrationTest.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void should_get_user_by_openId_if_exist() {
        final User result =
                given()
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .queryParam("openId", "OPENID_1")
                        .when()
                        .get("/users")
                        .then()
                        .statusCode(HttpStatus.OK.value())
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .extract()
                        .as(User.class);

        assertNotNull(result);
        assertEquals("OPENID_1", result.getOpenId());

    }

    @Test
    @DatabaseSetup(value = "/dbunit/UserDBUnitIntegrationTest.xml", type = DatabaseOperation.CLEAN_INSERT)
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
    @DatabaseSetup(value = "/dbunit/UserDBUnitIntegrationTest.xml", type = DatabaseOperation.CLEAN_INSERT)
    @ExpectedDatabase(value = "/dbunit/UserDBUnitIntegrationTest_expect.xml", table = "user_tbl",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void should_add_new_user_when_post() {
        CreateUserRequest request = CreateUserRequest.builder()
                .avatarUrl("URL3").openId("OPENID_3").build();

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
        assertEquals("OPENID_3", result.getOpenId());
    }


}