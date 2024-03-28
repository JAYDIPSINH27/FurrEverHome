package com.furreverhome.Furrever_Home.unittests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furreverhome.Furrever_Home.controller.PetAdopterController;
import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetAdoptionRequestDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.petadopter.PetAdopterDto;
import com.furreverhome.Furrever_Home.dto.lostpet.LostPetDto;
import com.furreverhome.Furrever_Home.dto.lostpet.LostPetResponseDtoListDto;
import com.furreverhome.Furrever_Home.dto.petadopter.*;
import com.furreverhome.Furrever_Home.entities.LostPet;
import com.furreverhome.Furrever_Home.services.petadopterservices.LostPetService;
import com.furreverhome.Furrever_Home.services.petadopterservices.PetAdopterService;
import com.furreverhome.Furrever_Home.services.petservice.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PetAdopterControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PetAdopterService petAdopterService;
    @Mock
    private PetService petService;
    @Mock
    private LostPetService lostPetService;

    @InjectMocks
    private PetAdopterController petAdopterController;

    private PetAdopterDto user;

    private ShelterResponseDto shelter;

    private PetResponseDto pet;
    private LostPet lostPet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(petAdopterController).build();

        user = new PetAdopterDto();
        user.setId(1L); // Assuming the User class has these fields
        user.setEmail("user@example.com");

        // Create the shelter object and set its properties
        shelter = new ShelterResponseDto();
        shelter.setId(1L);
        shelter.setName("Happy Paws Shelter");
        shelter.setCapacity(100L);
        shelter.setContact("555-1234");
        shelter.setImage("base64EncodedImageString");
        shelter.setLicense("base64EncodedLicenseString");
        shelter.setAddress("1234 Street Name");
        shelter.setCity("CityName");
        shelter.setCountry("CountryName");
        shelter.setZipcode("123456");
        shelter.setRejected(false);

        //Create Pet
        pet = new PetResponseDto();
        pet.setType("Dog");
        pet.setBreed("Golden Retriever");
        pet.setColor("Golden");
        pet.setGender("Male");
        pet.setAdopted(false);

        lostPet = new LostPet();
        lostPet.setType("Dog");
        lostPet.setBreed("Golden Retriever");
        lostPet.setColour("Golden");
        lostPet.setGender("Male");
    }

    /**
     * Test case to verify that getAllShelters method returns the list returned by the service method.
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void testGetAllSheltersReturnsListReturnedByServiceMethod() throws Exception{
        List<ShelterResponseDto> expectedResults = new ArrayList<>();

        expectedResults.add(shelter);

        given(petAdopterService.getAllShelter()).willReturn(expectedResults);

        mockMvc.perform(get("/api/petadopter/shelters"))
                .andExpect(status().isOk());
    }

    /**
     * Test case to verify the retrieval of a pet adopter by user ID when the user exists.
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void testGetPetAdopterByUserWhenUserExist() throws Exception{

        Long userId = 1L;
        given(petAdopterService.getPetAdopterDetailsById(userId)).willReturn(user);
        mockMvc.perform(get("/api/petadopter/{userId}",userId))
                .andExpect(status().isOk());

    }

    /**
     * Test case to verify the retrieval of a pet adopter by user ID when the user does not exist.
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void testGetPetAdopterByUserWhenUserDoesNotExist() throws Exception{

        Long userId = 2L;
        given(petAdopterService.getPetAdopterDetailsById(userId)).willReturn(null);
        mockMvc.perform(get("/api/petadopter/{userId}",userId))
                .andExpect(status().isNotFound());
    }

    /**
     * Test case to verify the search for shelters.
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void testSearchShelter() throws Exception{

        ShelterResponseDtoListDto expectedResults = new ShelterResponseDtoListDto();

        SearchShelterDto searchShelterDto = new SearchShelterDto();

        given(petAdopterService.searchShelter(searchShelterDto)).willReturn(expectedResults);

        mockMvc.perform(post("/api/petadopter/searchshelter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(searchShelterDto)))
                .andExpect(status().isOk());
    }

    /**
     * Test case to verify searching for pets.
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void testSearchPet() throws Exception{

        PetResponseDtoListDto expectedResults = new PetResponseDtoListDto();

        SearchPetDto searchPetDto = new SearchPetDto();

        given(petAdopterService.searchPet(searchPetDto)).willReturn(expectedResults);

        mockMvc.perform(post("/api/petadopter/searchpet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(searchPetDto)))
                .andExpect(status().isOk());
    }

    /**
     * Test case to verify requesting to adopt a pet.
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void testAdoptPetRequest() throws Exception{
        PetAdoptionRequestDto petAdoptionRequestDto = new PetAdoptionRequestDto();

        GenericResponse g = Mockito.mock(GenericResponse.class);

        given(petAdopterService.adoptPetRequest(petAdoptionRequestDto)).willReturn(g);

        mockMvc.perform(post("/api/petadopter/pet/adopt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(petAdoptionRequestDto)))
                .andExpect(status().isOk());
    }

    /**
     * Test case to verify retrieving pet information.
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void testGetPetInfo() throws Exception{

        PetDto petDto = new PetDto();
        Long petID = 1l;

        given(petService.getPetInfo(petID)).willReturn(petDto);

        mockMvc.perform(get("/api/petadopter/pets/{petID}",petID))
                .andExpect(status().isOk());
    }

    /**
     * Test case to verify searching for lost pets.
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void testSearchLostPet() throws Exception{

        SearchPetDto searchPetDto = new SearchPetDto();

        LostPetResponseDtoListDto lostPetResponseDtoListDto = new LostPetResponseDtoListDto();

        given(lostPetService.searchLostPet(searchPetDto)).willReturn(lostPetResponseDtoListDto);

        mockMvc.perform(post("/api/petadopter/searchlostpet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(searchPetDto)))
                .andExpect(status().isOk());
    }

    /**
     * Test case to verify retrieving lost pets by user ID.
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void testGetLostPetBy() throws Exception{

        LostPetResponseDtoListDto lostPetResponseDtoListDto = new LostPetResponseDtoListDto();
        Long userId = 1L;
        given(lostPetService.getLostPetListByUser(userId)).willReturn(lostPetResponseDtoListDto);

        mockMvc.perform(get("/api/petadopter/lostpet/{userId}",userId))
                .andExpect(status().isOk());
    }

    /**
     * Test case to verify updating lost pet details.
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void testUpdateLostPetDetails() throws Exception{
        LostPetDto lostPetDto = new LostPetDto();

        given(lostPetService.updateLostPetDetails(lostPetDto)).willReturn(true);

        mockMvc.perform(post("/api/petadopter/lostpet/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(lostPetDto)))
                .andExpect(status().isOk());

    }

    /**
     * Test case to verify that a request exists for adopting a pet when both pet and adopter exist.
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void requestExistsBothExistsReturns200() throws Exception {
        long petID = 2L;
        long petAdopterID = 2L;
        given(petAdopterService.requestExists(petID, petAdopterID)).willReturn(true);
        mockMvc.perform(get("/api/petadopter/pet/adopt/requestexists")
                        .param("petID", String.valueOf(petID))
                        .param("petAdopterID", String.valueOf(petAdopterID)))
                .andExpect(status().isOk());
    }

    /**
     * Test case to verify that a request exists for adopting a pet when the adopter does not exist.
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void requestExistsPetAdopterDoesNotExistReturns404() throws Exception {
        long petID = 2L;
        long petAdopterID = 2L;
        given(petAdopterService.requestExists(petID, petAdopterID)).willReturn(false);
        mockMvc.perform(get("/api/petadopter/pet/adopt/requestexists")
                        .param("petID", String.valueOf(petID))
                        .param("petAdopterID", String.valueOf(petAdopterID)))
                .andExpect(status().isNotFound());
    }

    /**
     * util function to covert object to json
     * @param obj
     * @return
     */    private String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
