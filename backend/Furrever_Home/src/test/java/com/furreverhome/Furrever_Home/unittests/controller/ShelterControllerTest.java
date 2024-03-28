package com.furreverhome.Furrever_Home.unittests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furreverhome.Furrever_Home.controller.ShelterController;
import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetAdoptionRequestResponseDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetVaccineDto;
import com.furreverhome.Furrever_Home.dto.shelter.RegisterPetRequest;
import com.furreverhome.Furrever_Home.services.petservice.PetService;
import com.furreverhome.Furrever_Home.services.shelterService.ShelterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShelterControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ShelterServiceImpl shelterService;

    @Mock
    private PetService petService;

    @InjectMocks
    private ShelterController shelterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(shelterController).build();
    }

    /**
     * Test case to verify successful registration of a pet in the shelter.
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    void registerPetInShelterSuccessfulRegistration() throws Exception {
        RegisterPetRequest request = new RegisterPetRequest();
        request.setType("Dog");
        request.setBreed("Labrador");
        request.setColour("Black");
        request.setGender("Male");
        PetDto expectedPetDto = new PetDto();
        expectedPetDto.setPetID(1L);
        expectedPetDto.setType(request.getType());
        expectedPetDto.setBreed(request.getBreed());
        expectedPetDto.setColour(request.getColour());
        expectedPetDto.setGender(request.getGender());
        when(shelterService.registerPet(any(RegisterPetRequest.class))).thenReturn(expectedPetDto);
        mockMvc.perform(post("/api/shelter/registerPet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(request)))
                .andExpect(status().isOk());
    }

    /**
     * Test case to verify successful editing of a pet in the shelter.
     */
    @Test
    void editPetInShelter_Success() {
        RegisterPetRequest registerPetRequest = new RegisterPetRequest();
        registerPetRequest.setType("Dog");
        registerPetRequest.setBreed("Labrador");
        registerPetRequest.setColour("Black");
        registerPetRequest.setGender("Male");
        registerPetRequest.setBirthdate(new Date());
        registerPetRequest.setPetImage("dog.jpg");
        registerPetRequest.setPetMedicalHistory("Good health");
        registerPetRequest.setShelter(123L);
        Long petID = 1L;
        PetDto updatedPetDto = new PetDto();
        updatedPetDto.setType(registerPetRequest.getType());
        updatedPetDto.setBreed(registerPetRequest.getBreed());
        updatedPetDto.setColour(registerPetRequest.getColour());
        updatedPetDto.setGender(registerPetRequest.getGender());
        updatedPetDto.setBirthdate(registerPetRequest.getBirthdate());
        updatedPetDto.setPetImage(registerPetRequest.getPetImage());
        updatedPetDto.setPetMedicalHistory(registerPetRequest.getPetMedicalHistory());

        when(shelterService.editPet(eq(petID), any(RegisterPetRequest.class))).thenReturn(updatedPetDto);
        ResponseEntity<PetDto> responseEntity = shelterController.editPetInShelter(petID, registerPetRequest);

        verify(shelterService, times(1)).editPet(eq(petID), any(RegisterPetRequest.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedPetDto, responseEntity.getBody());
    }

    /**
     * Test case to verify successful deletion of a pet in the shelter.
     * Verifies that the pet is successfully deleted and returns a 200 OK response with a message indicating success.
     */
    @Test
    void deletePetInShelterDeletedSuccessfully() {
        Long petID = 2L;
        when(shelterService.deletePet(eq(petID))).thenReturn(new GenericResponse("Pet deleted."));
        ResponseEntity<GenericResponse> responseEntity = shelterController.deletePetInShelter(petID);
        verify(shelterService, times(1)).deletePet(eq(petID));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Pet deleted.", responseEntity.getBody().getMessage());
    }

    /**
     * Test case to verify successful addition of a vaccine for a pet in the shelter.
     * Verifies that the vaccine is successfully added and returns a 200 OK response.
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    void addVaccineSuccessfulAddition() throws Exception {
        long petId = 2L;
        PetVaccineDto petVaccineDto = new PetVaccineDto();
        petVaccineDto.setVaccineName("Rabies");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        petVaccineDto.setDate(sdf.parse("03/05/2021"));
        petVaccineDto.setVaccineGiven(true);
        GenericResponse expectedResponse = new GenericResponse("Vaccination added.");
        when(petService.addVaccinationDetails(any(PetVaccineDto.class), any(Long.class))).thenReturn(expectedResponse);
        mockMvc.perform(post("/api/shelter/{petID}/addvaccine", petId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(petVaccineDto)))
                .andExpect(status().isOk());
    }

    /**
     * Test case to verify successful change of adopted status for a pet in the shelter.
     * Verifies that the adopted status is successfully changed and returns a 200 OK response.
     */
    @Test
    void changeAdoptedStatusChangedSuccessfully() {
        Long petId = 1L;
        String status = "adopted";
        when(shelterService.changeAdoptedStatus(eq(petId), eq(status))).thenReturn(true);
        ResponseEntity<?> responseEntity = shelterController.changeAdoptedStatus(petId, status);
        verify(shelterService, times(1)).changeAdoptedStatus(eq(petId), eq(status));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Test case to verify failure in changing adopted status for a pet in the shelter.
     * Verifies that the adopted status change fails and returns a 404 Not Found response.
     */
    @Test
    void changeAdoptedStatusFailureReturnsNotFound() {
        Long petId = 1L;
        String status = "adopted";
        when(shelterService.changeAdoptedStatus(eq(petId), eq(status))).thenReturn(false);
        ResponseEntity<?> responseEntity = shelterController.changeAdoptedStatus(petId, status);
        verify(shelterService, times(1)).changeAdoptedStatus(eq(petId), eq(status));
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    /**
     * Test case to verify successful retrieval of pet information by ID.
     * Verifies that the pet information is successfully retrieved and returns a 200 OK response with the pet DTO.
     */
    @Test
    void getPetInfoRetrievedSuccessfully() {
        Long petID = 1L;
        PetDto petDto = new PetDto();

        when(petService.getPetInfo(eq(petID))).thenReturn(petDto);
        ResponseEntity<PetDto> responseEntity = shelterController.getPetInfo(petID);

        verify(petService, times(1)).getPetInfo(eq(petID));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(petDto, responseEntity.getBody());
    }

    /**
     * Test case to verify successful retrieval of pet adoption requests by pet ID.
     * Verifies that the pet adoption requests are successfully retrieved and returns a 200 OK response with the adoption request DTO.
     */
    @Test
    void getPetAdoptionRequestsRetrievedSuccessfully() {
        Long petID = 1L;
        PetAdoptionRequestResponseDto adoptionRequestResponseDto = new PetAdoptionRequestResponseDto();
        when(shelterService.getPetAdoptionRequests(eq(petID))).thenReturn(adoptionRequestResponseDto);
        ResponseEntity<PetAdoptionRequestResponseDto> responseEntity = shelterController.getPetAdoptionRequests(petID);

        verify(shelterService, times(1)).getPetAdoptionRequests(eq(petID));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(adoptionRequestResponseDto, responseEntity.getBody());
    }

    /**
     * Test case to verify successful retrieval of pets in a shelter by shelter ID.
     * Verifies that the pets in the shelter are successfully retrieved and returns a 200 OK response with the list of pet DTOs.
     */
    @Test
    void getPetInShelterReturnsListOfPets() {
        Long shelterID = 1L;
        List<PetDto> pets = new ArrayList<>();
        PetDto pet1 = new PetDto();
        pet1.setPetID(1L);
        pet1.setType("Dog");
        pets.add(pet1);
        when(shelterService.getPetsForShelter(eq(shelterID))).thenReturn(pets);
        ResponseEntity<List<PetDto>> responseEntity = shelterController.getPetInShelter(shelterID);

        verify(shelterService, times(1)).getPetsForShelter(eq(shelterID));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(pets.size(), responseEntity.getBody().size());
        PetDto returnedPet = responseEntity.getBody().get(0);
        assertEquals(pet1.getPetID(), returnedPet.getPetID());
        assertEquals(pet1.getType(), returnedPet.getType());
    }

    /**
     * util function to covert object to json
     * @param obj
     * @return
     */
    private String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
