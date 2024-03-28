package com.furreverhome.Furrever_Home.integrationtests;

import com.furreverhome.Furrever_Home.dto.petadopter.SearchPetDto;
import com.furreverhome.Furrever_Home.dto.petadopter.SearchShelterDto;
import com.furreverhome.Furrever_Home.dto.shelter.RegisterPetRequest;
import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.enums.Role;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.PetRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import io.restassured.response.Response;
import org.aspectj.lang.annotation.Before;
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
public class PetAdopterIntegrationTest {
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
        shelter.setCity("Halifax");
        shelter.setCapacity(100L);
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
     * Test searching for shelters.
     */
    @Test
    public void testSearchShelterSuccessThenOK(){
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", "testpetadopter@gmail.com", "Jp@32padhiyar");

        // Send POST request to login endpoint
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(serverLink+port+"/api/auth/signin");


        String token = response.jsonPath().getString("token");

        // Arrange data in the database for querying
        User shelterUser1 = new User();
        shelterUser1.setEmail("shel1t@gmail.com");
        shelterUser1.setPassword(new BCryptPasswordEncoder().encode("Jp@32padhiyar"));
        shelterUser1.setVerified(true);
        shelterUser1.setRole(Role.SHELTER);
        userRepository.save(shelterUser1);

        Shelter shelter1 = new Shelter();
        shelter1.setName("shelter1");
        shelter1.setCity("Halifax");
        shelter1.setCapacity(100L);
        shelter1.setAccepted(true);
        shelter1.setRejected(false);
        shelter1.setUser(shelterUser1);
        shelterRepository.save(shelter1);

        SearchShelterDto searchShelterDto = new SearchShelterDto();
        searchShelterDto.setCapacity(100L);
        searchShelterDto.setCity("Halifax");

        // Send POST request to the Search Shelter endpoint Act
        // We have 2 shelters in the database: 1st created in setUp() and 2nd
        // created just above
        Response searchResponse = given().header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(searchShelterDto)
                .post(serverLink+port+"/api/petadopter/searchshelter");

        assertNotNull(searchResponse.asString());
        assertEquals(2, searchResponse.jsonPath().getList("shelterResponseDtoList").size());

        // data cleanup
        shelterRepository.delete(shelter1);
        userRepository.delete(shelterUser1);
    }

    /**
     * Test searching for pets.
     */
    @Test
    public void testSearchPetSuccessThenOK(){
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", "testpetadopter@gmail.com", "Jp@32padhiyar");

        // Send POST request to login endpoint
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(serverLink+port+"/api/auth/signin");


        String token = response.jsonPath().getString("token");

        // Arrange data in the database for querying
        User shelterUser1 = new User();
        shelterUser1.setEmail("shel1t@gmail.com");
        shelterUser1.setPassword(new BCryptPasswordEncoder().encode("Jp@32padhiyar"));
        shelterUser1.setVerified(true);
        shelterUser1.setRole(Role.SHELTER);
        userRepository.save(shelterUser1);

        Shelter shelter1 = new Shelter();
        shelter1.setName("shelter1");
        shelter1.setCity("Halifax");
        shelter1.setCapacity(100L);
        shelter1.setAccepted(true);
        shelter1.setRejected(false);
        shelter1.setUser(shelterUser1);
        shelterRepository.save(shelter1);

        Pet pet1  = new Pet();
        pet1.setType("cat");
        pet1.setGender("male");
        pet1.setColour("green");
        pet1.setBreed("african");
        pet1.setShelter(shelter1);
        petRepository.save(pet1);

        Pet pet2  = new Pet();
        pet2.setType("dog");
        pet2.setGender("male");
        pet2.setColour("black");
        pet2.setBreed("bulldog");
        pet2.setShelter(shelter1);
        petRepository.save(pet2);

        SearchPetDto searchPetDto = new SearchPetDto();
        searchPetDto.setBreed("african");
        searchPetDto.setType("cat");

        // Send POST request to the Search Pet endpoint Act
        Response searchResponse = given().header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(searchPetDto)
                .post(serverLink+port+"/api/petadopter/searchpet");

        assertNotNull(searchResponse.asString());
        assertEquals(1, searchResponse.jsonPath().getList("petResponseDtoList").size());

        // data cleanup
        petRepository.delete(pet1);
        petRepository.delete(pet2);
        shelterRepository.delete(shelter1);
        userRepository.delete(shelterUser1);
    }

    /**
     * Test retrieving all pets in a shelter.
     */
    @Test
    public void testgetAllPetsInShelterSuccessThenOK(){
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", "testpetadopter@gmail.com", "Jp@32padhiyar");

        // Send POST request to login endpoint
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(serverLink+port+"/api/auth/signin");


        String token = response.jsonPath().getString("token");

        // Arrange data in the database for querying
        User shelterUser1 = new User();
        shelterUser1.setEmail("shel1t@gmail.com");
        shelterUser1.setPassword(new BCryptPasswordEncoder().encode("Jp@32padhiyar"));
        shelterUser1.setVerified(true);
        shelterUser1.setRole(Role.SHELTER);
        userRepository.save(shelterUser1);

        Shelter shelter1 = new Shelter();
        shelter1.setName("shelter1");
        shelter1.setCity("Halifax");
        shelter1.setCapacity(100L);
        shelter1.setAccepted(true);
        shelter1.setRejected(false);
        shelter1.setUser(shelterUser1);
        shelter1 = shelterRepository.save(shelter1);

        Pet pet1  = new Pet();
        pet1.setType("cat");
        pet1.setGender("male");
        pet1.setColour("green");
        pet1.setBreed("african");
        pet1.setShelter(shelter1);
        petRepository.save(pet1);

        Pet pet2  = new Pet();
        pet2.setType("dog");
        pet2.setGender("male");
        pet2.setColour("black");
        pet2.setBreed("bulldog");
        pet2.setShelter(shelter1);
        petRepository.save(pet2);

        // Send POST request to the getAllPetsInShelter endpoint Act
        Response searchResponse = given().header("Authorization", "Bearer " + token)
                .pathParam("shelterID", shelter1.getId())
                .get(serverLink+port+"/api/petadopter/{shelterID}/pets");

        assertNotNull(searchResponse.asString());
        assertEquals(2, searchResponse.jsonPath().getList("petDto").size());

        // data cleanup
        petRepository.delete(pet1);
        petRepository.delete(pet2);
        shelterRepository.delete(shelter1);
        userRepository.delete(shelterUser1);
    }
}
