package com.furreverhome.Furrever_Home.unittests.services.petadopterservices;

import com.furreverhome.Furrever_Home.dto.petadopter.PetAdopterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.furreverhome.Furrever_Home.exception.UserNotFoundException;

import com.furreverhome.Furrever_Home.services.petadopterservices.impl.PetAdopterServiceImpl;
import com.furreverhome.Furrever_Home.repository.*;
import com.furreverhome.Furrever_Home.entities.*;
import com.furreverhome.Furrever_Home.dto.petadopter.*;
import org.springframework.data.domain.Example;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PetAdopterServiceImplTest {

    @Mock
    private ShelterRepository shelterRepository;

    @Mock
    private PetAdopterRepository petAdopterRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetAdopterServiceImpl petAdopterService;

    private User user;

    private Shelter shelter;

    private Pet pet;

    /**
     * Sets up the mock objects and initializes test data before each test method.
     */
    @BeforeEach
    void setup() {
        // Create a dummy user for the shelter
        user = new User();
        user.setId(1L); // Assuming the User class has these fields
        user.setEmail("user@example.com");
        user.setVerified(true);

        // Create the shelter object and set its properties
        shelter = new Shelter();
        shelter.setId(1L);
        shelter.setName("Happy Paws Shelter");
        shelter.setCapacity(100L);
        shelter.setContact("555-1234");
        shelter.setImageBase64("base64EncodedImageString");
        shelter.setLicense("base64EncodedLicenseString");
        shelter.setUser(user);
        shelter.setAddress("1234 Street Name");
        shelter.setCity("CityName");
        shelter.setCountry("CountryName");
        shelter.setZipcode("123456");
        shelter.setRejected(false);

        //Create Pet
        pet = new Pet();
        pet.setType("Dog");
        pet.setBreed("Golden Retriever");
        pet.setColour("Golden");
        pet.setGender("Male");
        pet.setAdopted(false);
        pet.setShelter(shelter);
    }

    /**
     * Tests getting all shelters.
     */
    @Test
    void testGetAllShelter() {
        // Mock the shelter list
        List<Shelter> shelters = Arrays.asList(shelter, shelter);
        when(shelterRepository.findAll()).thenReturn(shelters);

        // Act
        List<ShelterResponseDto> result = petAdopterService.getAllShelter();

        // Assert
        assertNotNull(result);
        assertEquals(shelters.size(), result.size());

        // Verify interaction with shelterRepository
        verify(shelterRepository, times(1)).findAll();
    }

    /**
     * Tests getting pet adopter details by ID when the user and pet adopter exist.
     */
    @Test
    void testGetPetAdopterDetailsByIdSuccess() {
        // Mock the pet adopter
        PetAdopter petAdopter = new PetAdopter();
        petAdopter.setUser(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(petAdopterRepository.findByUser(user)).thenReturn(Optional.of(petAdopter));

        // Act
        PetAdopterDto result = petAdopterService.getPetAdopterDetailsById(1L);

        // Assert
        assertNotNull(result);

        // Verify interaction
        verify(userRepository, times(1)).findById(1L);
        verify(petAdopterRepository, times(1)).findByUser(user);
    }

    /**
     * Tests getting pet adopter details by ID when the user does not exist.
     */
    @Test
    void testGetPetAdopterDetailsByIdUserNotFound() {
        // Mock userRepository to return empty
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> petAdopterService.getPetAdopterDetailsById(1L));

        // Verify interaction
        verify(userRepository, times(1)).findById(anyLong());
    }

    /**
     * Tests getting pet adopter details by ID when the pet adopter does not exist.
     */
    @Test
    void testGetPetAdopterDetailsByIdPetAdopterNotFound() {
        // Mock userRepository to return empty
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(petAdopterRepository.findByUser(user)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> petAdopterService.getPetAdopterDetailsById(2L));

        // Verify interaction
        verify(userRepository, times(1)).findById(anyLong());
        verify(petAdopterRepository, times(1)).findByUser(user);
    }

    /**
     * Tests searching for shelters.
     */
    @Test
    void testSearchShelter() {
        // Prepare mock shelter and criteria
        SearchShelterDto searchShelterDto = new SearchShelterDto();
        searchShelterDto.setName("shel");
        searchShelterDto.setCity("halifax");
        searchShelterDto.setCapacity(100L);
        List<Shelter> shelters = Arrays.asList(shelter, shelter);
        when(shelterRepository.findAll(any(Example.class))).thenReturn(shelters);

        // Act
        ShelterResponseDtoListDto result = petAdopterService.searchShelter(searchShelterDto);

        // Assert
        assertNotNull(result);
        assertEquals(shelters.size(), result.getShelterResponseDtoList().size());

        // Verify interaction
        verify(shelterRepository, times(1)).findAll(any(Example.class));
    }

    /**
     * Tests searching for pets.
     */
    @Test
    void testSearchPet() {
        // Prepare mock shelter and criteria
        SearchPetDto searchPetDto = new SearchPetDto();
        searchPetDto.setAge(10);
        searchPetDto.setBreed("doberman");
        searchPetDto.setType("dog");
        searchPetDto.setGender("male");
        searchPetDto.setColor("black");
        List<Pet> petList = Arrays.asList(pet, pet);
        when(petRepository.findAll(any(Example.class))).thenReturn(petList);

        // Act
        PetResponseDtoListDto result = petAdopterService.searchPet(searchPetDto);

        // Assert
        assertNotNull(result);
        assertEquals(petList.size(), result.getPetResponseDtoList().size());

        // Verify interaction
        verify(petRepository, times(1)).findAll(any(Example.class));
    }




}

