package com.furreverhome.Furrever_Home.unittests.services.petadopterservices;

import com.furreverhome.Furrever_Home.dto.lostpet.LostPetDto;
import com.furreverhome.Furrever_Home.dto.lostpet.LostPetResponseDtoListDto;
import com.furreverhome.Furrever_Home.dto.lostpet.RegisterLostPetDto;
import com.furreverhome.Furrever_Home.dto.petadopter.SearchPetDto;
import com.furreverhome.Furrever_Home.entities.LostPet;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.exception.UserNotFoundException;
import com.furreverhome.Furrever_Home.repository.LostPetRepository;
import com.furreverhome.Furrever_Home.repository.UserRepository;
import com.furreverhome.Furrever_Home.services.jwtservices.JwtService;
import com.furreverhome.Furrever_Home.services.petadopterservices.impl.LostPetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LostPetServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LostPetRepository lostPetRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LostPetServiceImpl lostPetService;

    private final String fakeJwt = "Bearer fake.jwt.token";
    private final String username = "user@example.com";
    private User user;
    private RegisterLostPetDto registerLostPetDto;

    /**
     * Sets up the mock objects and initializes test data before each test method.
     */
    @BeforeEach
    void setUp() {
        // Initialize the User and RegisterLostPetDto objects
        user = new User();
        user.setId(1L);
        user.setEmail(username);

        registerLostPetDto = new RegisterLostPetDto();
        registerLostPetDto.setBreed("Labrador");
        registerLostPetDto.setColour("Black");
        registerLostPetDto.setGender("Male");
        registerLostPetDto.setType("Dog");
        registerLostPetDto.setPetImage("image.jpg");
        registerLostPetDto.setPhone("1234567890");
        registerLostPetDto.setEmail(user.getEmail());
    }

    /**
     * Tests registering a lost pet successfully.
     */
    @Test
    void testRegisterLostPetSuccess() {
        // Setup the mock behavior
        when(jwtService.extractUserName(fakeJwt.substring(7))).thenReturn(username);
        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
        when(lostPetRepository.save(any(LostPet.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        LostPet result = lostPetService.registerLostPet(fakeJwt, registerLostPetDto);

        // Assert
        assertNotNull(result);
        assertEquals(registerLostPetDto.getBreed(), result.getBreed());
        assertEquals(registerLostPetDto.getColour(), result.getColour());
        assertEquals(registerLostPetDto.getGender(), result.getGender());
        assertEquals(registerLostPetDto.getType(), result.getType());
        assertEquals(registerLostPetDto.getPetImage(), result.getPetImage());
        assertEquals(registerLostPetDto.getPhone(), result.getPhone());
        assertEquals(registerLostPetDto.getEmail(), result.getEmail());
        assertNotNull(result.getUser());
        assertEquals(user.getEmail(), result.getUser().getEmail());

        // Verify the interactions
        verify(jwtService, times(1)).extractUserName(anyString());
        verify(userRepository, times(1)).findByEmail(username);
        verify(lostPetRepository, times(1)).save(any(LostPet.class));
    }

    /**
     * Tests registering a lost pet when the user is not found.
     */
    @Test
    void testRegisterLostPetUserNotFound() {
        // Setup
        String invalidJwt = "Bearer invalid.jwt.token";
        when(jwtService.extractUserName(invalidJwt.substring(7))).thenReturn("unknown@example.com");
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            lostPetService.registerLostPet(invalidJwt, registerLostPetDto);
        });

        // Verify the interactions
        verify(jwtService, times(1)).extractUserName(anyString());
        verify(userRepository, times(1)).findByEmail("unknown@example.com");
        verify(lostPetRepository, never()).save(any(LostPet.class));
    }

    /**
     * Tests retrieving all lost pets successfully.
     */
    @Test
    void testGetAllLostPets() {
        // Mock the LostPet
        LostPet lostPet1 = mock(LostPet.class);
        LostPet lostPet2 = mock(LostPet.class);
        when(lostPetRepository.findAll()).thenReturn(Arrays.asList(lostPet1, lostPet2));

        // Act
        List<LostPetDto> result = lostPetService.getAllLostPets();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verify the interaction with the repository
        verify(lostPetRepository, times(1)).findAll();
    }

    /**
     * Tests searching for lost pets.
     */
    @Test
    void testSearchLostPet() {
        SearchPetDto searchPetDto = new SearchPetDto();
        searchPetDto.setAge(10);
        searchPetDto.setBreed("doberman");
        searchPetDto.setType("dog");
        searchPetDto.setGender("male");
        searchPetDto.setColor("black");
        List<LostPet> lostPetList = Arrays.asList(Mockito.mock(LostPet.class), Mockito.mock(LostPet.class));
        when(lostPetRepository.findAll(any(Example.class))).thenReturn(lostPetList);

        // Act
        LostPetResponseDtoListDto result = lostPetService.searchLostPet(searchPetDto);

        // Assert
        assertNotNull(result);
        assertEquals(lostPetList.size(), result.getLostPetDtoList().size());

        // Verify interaction
        verify(lostPetRepository, times(1)).findAll(any(Example.class));
    }

    /**
     * Tests retrieving lost pets by user.
     */
    @Test
    void testReturnLostPetListWhenUserExistsWithLostPets() {
        LostPet lostPet1 = new LostPet();
        lostPet1.setUser(user);
        LostPet lostPet2 = new LostPet();
        lostPet2.setUser(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(lostPetRepository.findByUser(user)).thenReturn(Arrays.asList(lostPet1, lostPet2));

        // Act
        LostPetResponseDtoListDto result = lostPetService.getLostPetListByUser(1L);

        // Assert
        assertNotNull(result);
        assertEquals(result.getLostPetDtoList().size(), 2);

        // Verify
        verify(userRepository).findById(1L);
        verify(lostPetRepository, times(1)).findByUser(user);
    }

    /**
     * Tests throwing a {@link UserNotFoundException} when the user does not exist.
     */
    @Test
    void testThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> lostPetService.getLostPetListByUser(1L));

        // Verify
        verify(userRepository, times(1)).findById(1L);
        verify(lostPetRepository, never()).findByUser(any(User.class));
    }

    /**
     * Tests throwing a {@link RuntimeException} when the user exists but has no lost pets.
     */
    @Test
    void testThrowRuntimeExceptionWhenUserExistsButNoLostPets() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(lostPetRepository.findByUser(user)).thenReturn(Collections.emptyList());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> lostPetService.getLostPetListByUser(1L));

        // Verify
        verify(userRepository, times(1)).findById(1L);
        verify(lostPetRepository, times(1)).findByUser(user);
    }

    /**
     * Tests updating lost pet details when the lost pet exists.
     */
    @Test
    void testUpdateLostPetDetailsWhenLostPetExists() {
        LostPetDto lostPetDto = new LostPetDto();
        lostPetDto.setId(1L);
        lostPetDto.setPetImage("pet image");
        lostPetDto.setColour("black");
        lostPetDto.setPhone("896950004");
        lostPetDto.setEmail("example@gmail.com");
        lostPetDto.setType("dog");
        lostPetDto.setGender("male");

        LostPet lostPet = Mockito.mock(LostPet.class);

        when(lostPetRepository.findById(lostPetDto.getId())).thenReturn(Optional.of(lostPet));

        // Act
        boolean result = lostPetService.updateLostPetDetails(lostPetDto);

        // Assert
        assertTrue(result);
        verify(lostPetRepository, times(1)).save(lostPet);
    }

    /**
     * Tests updating lost pet details when the lost pet does not exist.
     */
    @Test
    void testUpdateLostPetDetailsWhenLostPetDoesNotExist() {
        LostPetDto lostPetDto = new LostPetDto();
        lostPetDto.setId(1L);
        lostPetDto.setPetImage("pet image");
        lostPetDto.setColour("black");
        lostPetDto.setPhone("896950004");
        lostPetDto.setEmail("example@gmail.com");
        lostPetDto.setType("dog");
        lostPetDto.setGender("male");

        when(lostPetRepository.findById(lostPetDto.getId())).thenReturn(Optional.empty());

        // Act
        boolean result = lostPetService.updateLostPetDetails(lostPetDto);

        // Assert
        assertFalse(result);
        verify(lostPetRepository, never()).save(any(LostPet.class));
    }

}
