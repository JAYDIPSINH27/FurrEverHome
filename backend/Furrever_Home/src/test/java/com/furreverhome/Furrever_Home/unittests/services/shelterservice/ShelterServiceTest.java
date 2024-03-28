package com.furreverhome.Furrever_Home.unittests.services.shelterservice;

import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetAdoptionRequestResponseDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.shelter.RegisterPetRequest;
import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.repository.AdopterPetRequestsRepository;
import com.furreverhome.Furrever_Home.repository.PetRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import com.furreverhome.Furrever_Home.services.petservice.PetServiceImpl;
import com.furreverhome.Furrever_Home.services.shelterService.ShelterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShelterServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private ShelterServiceImpl shelterService;

    @Mock
    private AdopterPetRequestsRepository adopterPetRequestsRepository;

    @Mock
    private ShelterRepository shelterRepository;

    /**
     * Test case for deleting a pet when the pet is not found.
     * Verifies that a RuntimeException is thrown and the pet is not deleted.
     */
    @Test
    void deletePetNotFoundTest(){
        Long petID = 2L;
        when(petRepository.existsById(petID)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> shelterService.deletePet(petID));
        verify(petRepository, never()).deleteById(anyLong());
    }

    /**
     * Test case for deleting a pet successfully.
     * Verifies that the pet is deleted and the appropriate response message is returned.
     */
    @Test
    void deletePetSuccess(){
        Long petID = 2L;
        when(petRepository.existsById(petID)).thenReturn(true);
        GenericResponse response = shelterService.deletePet(petID);
        verify(petRepository, times(1)).deleteById(petID);
        assertEquals("Pet deleted.", response.getMessage());
    }

    /**
     * Test case to verify that editing a pet fails when the pet is not found.
     * It ensures that attempting to edit a nonexistent pet results in an exception.
     * Also, verifies that no pet is saved.
     */
    @Test
    void editPetNotFound() {
        Long petID = 2L;
        RegisterPetRequest updatePetRequest = new RegisterPetRequest();
        updatePetRequest.setType("Dog");
        when(petRepository.findById(petID)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> shelterService.editPet(petID, updatePetRequest));
        verify(petRepository, never()).save(any(Pet.class));
    }

    /**
     * Test case for successfully editing a pet.
     * Verifies that the pet details are updated correctly.
     *
     * @throws ParseException if there is an error in parsing the birthdate
     */
    @Test
    void editPetSuccess() throws ParseException {
        Long petID = 2L;
        RegisterPetRequest updatePetRequest = new RegisterPetRequest();
        updatePetRequest.setType("Dog");
        updatePetRequest.setBreed("Labrador");
        updatePetRequest.setColour("Golden");
        updatePetRequest.setGender("Male");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        updatePetRequest.setBirthdate(sdf.parse("11/06/2001"));
        updatePetRequest.setPetImage("dog.jpg");
        Pet pet = new Pet();
        pet.setPetID(petID);
        when(petRepository.findById(petID)).thenReturn(Optional.of(pet));
        PetDto updatedPet = shelterService.editPet(petID, updatePetRequest);
        verify(petRepository, times(1)).findById(petID);
        verify(petRepository, times(1)).save(any(Pet.class));
        assertEquals(updatePetRequest.getType(), updatedPet.getType());
        assertEquals(updatePetRequest.getBreed(), updatedPet.getBreed());
        assertEquals(updatePetRequest.getColour(), updatedPet.getColour());
        assertEquals(updatePetRequest.getGender(), updatedPet.getGender());
        assertEquals(updatePetRequest.getBirthdate(), updatedPet.getBirthdate());
        assertEquals(updatePetRequest.getPetImage(), updatedPet.getPetImage());
    }

    /**
     * Test case for setting the adopted status of a pet to "Adopted".
     * Verifies that the adopted status is correctly set to true.
     */
    @Test
    void petAdoptedStatusSuccess() {
        Long petID = 2L;
        Pet pet = new Pet();
        pet.setPetID(petID);
        when(petRepository.findById(petID)).thenReturn(Optional.of(pet));
        boolean result = shelterService.changeAdoptedStatus(petID, "Adopted");
        verify(petRepository, times(1)).findById(petID);
        verify(petRepository, times(1)).save(pet);
        assertTrue(result);
        assertTrue(pet.isAdopted());
    }

    /**
     * Test case for setting the adopted status of a pet to "NotAdopted".
     * Verifies that the adopted status is correctly set to false.
     */
    @Test
    void petNotAdoptedStatusSuccess() {
        Long petID = 2L;
        Pet pet = new Pet();
        pet.setPetID(petID);
        when(petRepository.findById(petID)).thenReturn(Optional.of(pet));
        boolean result = shelterService.changeAdoptedStatus(petID, "NotAdopted");
        verify(petRepository, times(1)).findById(petID);
        verify(petRepository, times(1)).save(pet);
        assertTrue(result);
        assertFalse(pet.isAdopted());
    }

    /**
     * Test case to verify that an exception is thrown when attempting to retrieve adoption requests for a pet that does not exist.
     * It ensures that a RuntimeException is thrown with the appropriate message.
     */
    @Test
    void getPetAdoptionRequestsException() {
        Long petId = 2L;
        when(petRepository.findById(petId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> shelterService.getPetAdoptionRequests(petId));
        assertEquals("Requests with petID " + petId + " not found", exception.getMessage());
    }

    /**
     * Test case to verify successful retrieval of adoption requests for a pet.
     * It ensures that the correct PetAdoptionRequestResponseDto is returned with the expected pet ID.
     */
    @Test
    void getPetAdoptionRequestsSuccess() {
        Long petId = 2L;
        Pet pet = new Pet();
        pet.setPetID(petId);
        PetAdopter petAdopter = new PetAdopter();
        petAdopter.setId(1L);
        Optional<Pet> optionalPet = Optional.of(pet);
        when(petRepository.findById(petId)).thenReturn(optionalPet);
        when(adopterPetRequestsRepository.findPetAdoptersByPet(pet)).thenReturn(Collections.singletonList(petAdopter));
        PetAdoptionRequestResponseDto result = shelterService.getPetAdoptionRequests(petId);
        assertNotNull(result);
        assertEquals(petId, result.getPetID());
//        assertEquals(Collections.singletonList(petAdopter.getId()), result.getPetAdopters());
    }

    /**
     * Test case to verify that an exception is thrown when attempting to retrieve pets for a shelter that does not exist.
     * It ensures that a RuntimeException is thrown with the appropriate message.
     */
    @Test
    void getPetsForShelterShelterNotFound() {
        Long shelterId = 2L;
        when(shelterRepository.findById(shelterId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> shelterService.getPetsForShelter(shelterId));
        assertEquals("Shelter with ID " + shelterId + " not found", exception.getMessage());
    }

    /**
     * Test case to verify successful retrieval of pets for a shelter.
     * It ensures that an empty list of petDtos is returned when the shelter has no pets.
     */
    @Test
    void getPetsForShelterShelterFound() {
        Long shelterId = 2L;
        Shelter shelter = new Shelter();
        shelter.setId(shelterId);
        when(shelterRepository.findById(shelterId)).thenReturn(Optional.of(shelter));
        when(petRepository.findByShelter(shelter)).thenReturn(Collections.emptyList());
        var petDtos = shelterService.getPetsForShelter(shelterId);
        assertNotNull(petDtos);
        assertTrue(petDtos.isEmpty());
    }

    /**
     * Test case to verify the behavior when attempting to change the adopted status of a pet that does not exist.
     * It ensures that the method returns false when the pet is not found.
     */
    @Test
    void testChangePetAdoptedStatusWhenPetNotFound(){
        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = shelterService.changeAdoptedStatus(1L,"Adopted");

        assertFalse(result);
    }

    /**
     * Test case to verify the registration of a new pet.
     * It ensures that the pet is saved successfully and the correct PetDto is returned.
     */
    @Test
    void registerPet_ShouldSavePetAndReturnPetDto() throws Exception {

        RegisterPetRequest updatePetRequest = new RegisterPetRequest();
        updatePetRequest.setType("Dog");
        updatePetRequest.setBreed("Labrador");
        updatePetRequest.setColour("Golden");
        updatePetRequest.setGender("Male");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        updatePetRequest.setBirthdate(sdf.parse("11/06/2001"));
        updatePetRequest.setPetImage("dog.jpg");

        PetDto expectedDto = new PetDto();
        expectedDto.setType("Dog");
        expectedDto.setBreed("Labrador");
        expectedDto.setColour("Golden");
        expectedDto.setGender("Male");
        expectedDto.setBirthdate(sdf.parse("11/06/2001"));
        expectedDto.setPetImage("dog.jpg");
        expectedDto.setPetMedicalHistory(null);
        expectedDto.setShelter(null);
        expectedDto.setAdopted(false);


        when(shelterRepository.findById(updatePetRequest.getShelter())).thenReturn(Optional.empty());

        PetDto result = shelterService.registerPet(updatePetRequest);

        assertNotNull(result);
        assertEquals(expectedDto, result);

        verify(shelterRepository).findById(updatePetRequest.getShelter());
        verify(petRepository).save(any(Pet.class));

    }


}
