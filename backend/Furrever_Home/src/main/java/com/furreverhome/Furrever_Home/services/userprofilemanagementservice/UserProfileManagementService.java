package com.furreverhome.Furrever_Home.services.userprofilemanagementservice;

import com.furreverhome.Furrever_Home.dto.profile.*;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.ShelterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileManagementService {

    private final PetAdopterRepository petAdopterRepository;
    private final ShelterRepository shelterRepository;
    private final UserProfileMapper userProfileMapper;

    /**
     * Updates the user profile with the given user ID using the information provided
     * in the {@code UpdateUserProfileRequestDto}.
     *
     * @param userId                       The ID of the user whose profile needs to be updated.
     * @param updateUserProfileRequestDto The DTO containing the updated user profile information.
     * @return                             The response DTO containing the updated user profile details.
     * @throws EntityNotFoundException    If the user with the given ID is not found.
     */
    public UpdateUserProfileResponseDto updateUserProfile(Long userId, UpdateUserProfileRequestDto updateUserProfileRequestDto) {
        PetAdopter petAdopter;
        petAdopter = petAdopterRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        PetAdopter updatedPetAdopter = userProfileMapper.toPetAdopterEntity(updateUserProfileRequestDto, petAdopter);
        petAdopterRepository.save(updatedPetAdopter);

        return userProfileMapper.toUpdateUserProfileResponseDto(updatedPetAdopter);
    }

    /**
     * Retrieves the user profile with the given user ID.
     *
     * @param userId              The ID of the user whose profile needs to be retrieved.
     * @return                    The response DTO containing the user profile details.
     * @throws EntityNotFoundException    If the user with the given ID is not found.
     */
    public UpdateUserProfileResponseDto getUserProfile(Long userId) {
        PetAdopter petAdopter;
        petAdopter = petAdopterRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return userProfileMapper.toUpdateUserProfileResponseDto(petAdopter);
    }

    /**
     * Updates the shelter profile with the given shelter ID using the information provided
     * in the {@code UpdateShelterProfileRequestDto}.
     *
     * @param shelterId                    The ID of the shelter whose profile needs to be updated.
     * @param updateShelterProfileRequestDto The DTO containing the updated shelter profile information.
     * @return                              The response DTO containing the updated shelter profile details.
     * @throws EntityNotFoundException     If the shelter with the given ID is not found.
     */
    public UpdateShelterProfileResponseDto updateShelterProfile(Long shelterId, UpdateShelterProfileRequestDto updateShelterProfileRequestDto) {
        Shelter shelter;
        shelter = shelterRepository.findByUserId(shelterId)
                .orElseThrow(() -> new EntityNotFoundException("Shelter not found"));

        Shelter updatedShelter = userProfileMapper.toShelterEntity(updateShelterProfileRequestDto, shelter);
        shelterRepository.save(updatedShelter);

        return userProfileMapper.toUpdateShelterProfileResponseDto(updatedShelter);
    }

    /**
     * Retrieves the shelter profile with the given shelter ID.
     *
     * @param shelterId             The ID of the shelter whose profile needs to be retrieved.
     * @return                     The response DTO containing the shelter profile details.
     * @throws EntityNotFoundException    If the shelter with the given ID is not found.
     */
    public UpdateShelterProfileResponseDto getShelterProfile(Long shelterId) {
        Shelter shelter;
        shelter = shelterRepository.findByUserId(shelterId)
                .orElseThrow(() -> new EntityNotFoundException("Shelter not found"));

        return userProfileMapper.toUpdateShelterProfileResponseDto(shelter);
    }

}
