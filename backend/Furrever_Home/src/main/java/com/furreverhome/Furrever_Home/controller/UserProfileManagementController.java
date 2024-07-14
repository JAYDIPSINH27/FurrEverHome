package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.dto.profile.UpdateShelterProfileRequestDto;
import com.furreverhome.Furrever_Home.dto.profile.UpdateShelterProfileResponseDto;
import com.furreverhome.Furrever_Home.dto.profile.UpdateUserProfileRequestDto;
import com.furreverhome.Furrever_Home.dto.profile.UpdateUserProfileResponseDto;
import com.furreverhome.Furrever_Home.services.userprofilemanagementservice.UserProfileManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserProfileManagementController {

    private final UserProfileManagementService userProfileManagementService;

    /**
     * Updates the user profile with the specified user ID.
     *
     * @param userId The ID of the user whose profile is to be updated.
     * @param updateUserProfileRequestDto The request containing updated user profile details.
     * @return ResponseEntity containing the updated UpdateUserProfileResponseDto.
     */
    @PutMapping("/users/{userId}")
    ResponseEntity<UpdateUserProfileResponseDto> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody UpdateUserProfileRequestDto updateUserProfileRequestDto) {
        return ResponseEntity.ok(userProfileManagementService.updateUserProfile(userId, updateUserProfileRequestDto));

    }

    /**
     * Retrieves the user profile by the specified user ID.
     *
     * @param userId The ID of the user whose profile is to be retrieved.
     * @return ResponseEntity containing the UpdateUserProfileResponseDto representing the user profile.
     */
    @GetMapping("/users/{userId}")
    ResponseEntity<UpdateUserProfileResponseDto> updateUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userProfileManagementService.getUserProfile(userId));
    }

    /**
     * Updates the shelter profile with the specified shelter ID.
     *
     * @param shelterId The ID of the shelter whose profile is to be updated.
     * @param updateShelterProfileRequestDto The request containing updated shelter profile details.
     * @return ResponseEntity containing the updated UpdateShelterProfileResponseDto.
     */
    @PutMapping("/shelters/{shelterId}")
    ResponseEntity<UpdateShelterProfileResponseDto> updateShelterProfile(
            @PathVariable Long shelterId,
            @RequestBody UpdateShelterProfileRequestDto updateShelterProfileRequestDto) {
        return ResponseEntity.ok(userProfileManagementService.updateShelterProfile(shelterId, updateShelterProfileRequestDto));
    }

    /**
     * Retrieves the shelter profile by the specified shelter ID.
     *
     * @param shelterId The ID of the shelter whose profile is to be retrieved.
     * @return ResponseEntity containing the UpdateShelterProfileResponseDto representing the shelter profile.
     */
    @GetMapping("/shelters/{shelterId}")
    ResponseEntity<UpdateShelterProfileResponseDto> getShelterProfile(@PathVariable Long shelterId) {
        return ResponseEntity.ok(userProfileManagementService.getShelterProfile(shelterId));
    }
}
