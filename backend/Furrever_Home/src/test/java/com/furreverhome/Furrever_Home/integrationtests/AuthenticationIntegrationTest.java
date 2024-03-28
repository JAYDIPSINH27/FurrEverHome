package com.furreverhome.Furrever_Home.integrationtests;

import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

import static io.restassured.RestAssured.given;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@AutoConfigureMockMvc
@EnableJpaRepositories("com.furreverhome.Furrever_Home.repository")
public class AuthenticationIntegrationTest {

    public String serverLink = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetAdopterRepository petAdopterRepository;

    @Autowired
    private ShelterRepository shelterRepository;

    User shelterUser;

    Shelter shelter;

    User petAdopterUser;

    PetAdopter petAdopter;
    @BeforeEach
    public void setup() {
        shelterUser = new User();

        shelterUser.setEmail("testshelter@gmail.com");
        shelterUser.setRole(Role.SHELTER);
        shelterUser.setVerified(Boolean.TRUE);
        shelterUser.setPassword(new BCryptPasswordEncoder().encode("Jp@32padhiyar"));
        userRepository.save(shelterUser);

        shelter = new Shelter();
        shelter.setUser(shelterUser);
        shelter.setAccepted(true);
        shelter.setRejected(false);
        shelter.setName("testShelter");
        shelterRepository.save(shelter);

        petAdopterUser = new User();

        petAdopterUser.setEmail("testpetadopter@gmail.com");
        petAdopterUser.setRole(Role.PETADOPTER);
        petAdopterUser.setVerified(Boolean.TRUE);
        petAdopterUser.setPassword(new BCryptPasswordEncoder().encode("Jp@32padhiyar"));
        userRepository.save(petAdopterUser);

        petAdopter = new PetAdopter();
        petAdopter.setUser(petAdopterUser);
        petAdopter.setFirstname("testpetadopter");
        petAdopterRepository.save(petAdopter);
    }

    @AfterEach
    public void cleanup() {
        petAdopterRepository.delete(petAdopter);
        shelterRepository.delete(shelter);
        userRepository.delete(petAdopterUser);
        userRepository.delete(shelterUser);
    }
    /**
     * Tests the sign-in process for a shelter user with successful authentication.
     * Verifies that a valid token and user role "SHELTER" are returned upon successful sign-in.
     */
    @Test
    public void testShelterSigninSuccessThenOK(){
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", "testshelter@gmail.com", "Jp@32padhiyar");

        // Send POST request to login endpoint
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(serverLink+port+"/api/auth/signin");

        String token = response.jsonPath().getString("token");
        String userRole = response.jsonPath().getString("userRole");
        assertNotNull(token);
        assertEquals("SHELTER", userRole);
    }

    /**
     * Tests the sign-in process for a pet adopter user with successful authentication.
     * Verifies that a valid token and user role "PETADOPTER" are returned upon successful sign-in.
     */
    @Test
    public void testPetAdopterSigninSuccessThenOK(){
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", "testpetadopter@gmail.com", "Jp@32padhiyar");

        // Send POST request to login endpoint
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(serverLink+port+"/api/auth/signin");

        String token = response.jsonPath().getString("token");
        String userRole = response.jsonPath().getString("userRole");
        assertNotNull(token);
        assertEquals("PETADOPTER", userRole);
    }
}
