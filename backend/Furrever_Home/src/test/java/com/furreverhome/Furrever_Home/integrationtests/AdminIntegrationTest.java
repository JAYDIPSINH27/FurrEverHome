package com.furreverhome.Furrever_Home.integrationtests;

import com.furreverhome.Furrever_Home.dto.auth.SigninRequest;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
class AdminIntegrationTest {
    private String token;
    public String serverLink = "http://localhost:";

    @LocalServerPort
    private int port;

    /**
     * Sets up the necessary authentication token before each test method.
     */
    @BeforeEach
    public void setUp() {
        SigninRequest signinRequest = new SigninRequest();
        signinRequest.setEmail("admin@gmail.com");
        signinRequest.setPassword("Jp@32padhiyar");
        // Send POST request to login endpoint
        Response response = given()
                .header("Content-Type", "application/json")
                .body(signinRequest)
                .post(serverLink+port+"/api/auth/signin");

        // Check if the response body is not empty and is a proper JSON
        if(response.body().asString().trim().isEmpty() || !response.contentType().contains("application/json")) {
            throw new IllegalStateException("Response body is empty or not JSON: " + response.body().asString());
        }

        System.out.println(response.asString());
        // Extract token from response
        token = response.jsonPath().getString("token");
    }

    /**
     * Tests the getAllShelterSuccessThenOK integration scenario.
     * Verifies that when a request is made to get all shelters with valid authentication,
     * the response status code is 200 (OK).
     *
     */
    @Test
    public void testGetAllShelterSuccessThenOK(){
        System.out.println(serverLink+port);
        given().header("Authorization", "Bearer " + token)
                .when().get(serverLink+port + "/api/admin/shelters")
                .then().statusCode(200);
    }
}