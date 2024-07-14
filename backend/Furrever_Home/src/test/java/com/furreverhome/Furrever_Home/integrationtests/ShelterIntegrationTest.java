package com.furreverhome.Furrever_Home.integrationtests;

import com.furreverhome.Furrever_Home.dto.shelter.RegisterPetRequest;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.PetRepository;
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

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@AutoConfigureMockMvc
@EnableJpaRepositories("com.furreverhome.Furrever_Home.repository")
public class ShelterIntegrationTest {
    public String serverLink = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetAdopterRepository petAdopterRepository;

    @Autowired
    private ShelterRepository shelterRepository;

    @Autowired
    private PetRepository petRepository;

    User shelterUser;

    Shelter shelter;

    User petAdopterUser;

    PetAdopter petAdopter;

    /**
     * Set up the test environment before each test method.
     */
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

    /**
     * Clean up the test environment after each test method.
     */
    @AfterEach
    public void cleanup() {
        petAdopterRepository.delete(petAdopter);
        shelterRepository.delete(shelter);
        userRepository.delete(petAdopterUser);
        userRepository.delete(shelterUser);
    }

    /**
     * Test registering a pet.
     */
    @Test
    public void testRegisterPetSuccessThenOK(){
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", "testshelter@gmail.com", "Jp@32padhiyar");

        // Send POST request to login endpoint
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(serverLink+port+"/api/auth/signin");


        String token = response.jsonPath().getString("token");
        int shelterId = response.jsonPath().getInt("shelterId");

        RegisterPetRequest registerPetRequest = new RegisterPetRequest();
        registerPetRequest.setShelter(shelterId);
        registerPetRequest.setGender("male");
        registerPetRequest.setColour("brown");
        registerPetRequest.setBreed("dobarman");
        registerPetRequest.setType("dog");

        // Send POST request to the register pet endpoint Act
        Response registerPet = given().header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(registerPetRequest)
                .post(serverLink+port+"/api/shelter/registerPet");

        assertNotNull(registerPet.asString());
        assertEquals("dog" ,registerPet.jsonPath().getString("type"));

        // data cleanup
        Long petId = registerPet.jsonPath().getLong("petID");
        petRepository.deleteById(petId);
    }

    /**
     * Test getting pet information.
     */
    @Test
    public void testGetPetInfoSuccessThenOK(){
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", "testshelter@gmail.com", "Jp@32padhiyar");

        // Send POST request to login endpoint
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(serverLink+port+"/api/auth/signin");


        String token = response.jsonPath().getString("token");
        int shelterId = response.jsonPath().getInt("shelterId");

        RegisterPetRequest registerPetRequest = new RegisterPetRequest();
        registerPetRequest.setShelter(shelterId);
        registerPetRequest.setGender("male");
        registerPetRequest.setColour("orange");
        registerPetRequest.setBreed("pomarian");
        registerPetRequest.setType("cat");

        // Send POST request to the register pet endpoint Arrange
        Response registerPet = given().header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(registerPetRequest)
                .post(serverLink+port+"/api/shelter/registerPet");


        // Send Get request to the getPetInfo endpoint Act
        Response getPetInfo = given().header("Authorization", "Bearer " + token)
                .pathParam("petID", registerPet.jsonPath().getLong("petID"))
                .get(serverLink+port+"/api/shelter/{petID}");

        assertNotNull(getPetInfo.asString());
        assertEquals("cat" , getPetInfo.jsonPath().getString("type"));
        assertEquals("pomarian" , getPetInfo.jsonPath().getString("breed"));
        assertEquals("male" , getPetInfo.jsonPath().getString("gender"));
        assertEquals("orange" , getPetInfo.jsonPath().getString("colour"));

        // data cleanup
        Long petId = registerPet.jsonPath().getLong("petID");
        petRepository.deleteById(petId);
    }
}
