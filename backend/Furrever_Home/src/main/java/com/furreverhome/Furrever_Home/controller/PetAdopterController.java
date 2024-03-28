package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.dto.Pet.PetAdoptionRequestDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.petadopter.PetAdopterDto;
import com.furreverhome.Furrever_Home.dto.lostpet.LostPetDto;
import com.furreverhome.Furrever_Home.dto.lostpet.LostPetResponseDtoListDto;
import com.furreverhome.Furrever_Home.dto.lostpet.RegisterLostPetDto;
import com.furreverhome.Furrever_Home.dto.petadopter.SearchPetDto;
import com.furreverhome.Furrever_Home.dto.petadopter.SearchShelterDto;
import com.furreverhome.Furrever_Home.dto.petadopter.ShelterResponseDto;
import com.furreverhome.Furrever_Home.entities.LostPet;
import com.furreverhome.Furrever_Home.services.petadopterservices.LostPetService;
import com.furreverhome.Furrever_Home.services.petadopterservices.PetAdopterService;
import com.furreverhome.Furrever_Home.services.shelterService.ShelterService;
import jakarta.validation.Valid;
import com.furreverhome.Furrever_Home.services.petservice.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/petadopter")
@RequiredArgsConstructor
public class PetAdopterController {

    private final PetAdopterService petAdopterService;
    private final LostPetService lostPetService;
    private final PetService petService;

    private final ShelterService shelterService;

    /**
     * Retrieves all shelters.
     * @return ResponseEntity containing a list of ShelterResponseDto.
     */
    @GetMapping("/shelters")
    public ResponseEntity<List<ShelterResponseDto>> getAllShelters() {
        List<ShelterResponseDto> shelterResponseDtoList = petAdopterService.getAllShelter();
        return ResponseEntity.ok(shelterResponseDtoList);
    }

    /**
     * Retrieves pet adopter details by user ID.
     * @param userId The ID of the user.
     * @return ResponseEntity containing the PetAdopterDto.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<PetAdopterDto> getPetAdopterByUser (@PathVariable Long userId) {
        PetAdopterDto petAdopterDto = petAdopterService.getPetAdopterDetailsById(userId);
        if(petAdopterDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(petAdopterDto);
    }

    /**
     * Searches for shelters based on search criteria.
     * @param searchShelterDto The search criteria.
     * @return ResponseEntity containing the search results.
     */
    @PostMapping("/searchshelter")
    public ResponseEntity<?> searchShelter(@RequestBody SearchShelterDto searchShelterDto) {
        return ResponseEntity.ok(petAdopterService.searchShelter(searchShelterDto));
    }

    /**
     * Searches for pets based on search criteria.
     * @param searchPetDto The search criteria.
     * @return ResponseEntity containing the search results.
     */
    @PostMapping("/searchpet")
    public ResponseEntity<?> searchPet(@RequestBody SearchPetDto searchPetDto) {
        return ResponseEntity.ok(petAdopterService.searchPet(searchPetDto));
    }

    /**
     * Handles a pet adoption request.
     * @param petAdoptionRequestDto The adoption request details.
     * @return ResponseEntity containing the result of the adoption request.
     */
    @PostMapping("/pet/adopt")
    public ResponseEntity<?> adoptPetRequest(@RequestBody PetAdoptionRequestDto petAdoptionRequestDto){
        return ResponseEntity.ok(petAdopterService.adoptPetRequest(petAdoptionRequestDto));
    }

    /**
     * Retrieves information about a pet by its ID.
     * @param petID The ID of the pet.
     * @return ResponseEntity containing the PetDto.
     */
    @GetMapping("/pets/{petID}")
    public ResponseEntity<PetDto> getPetInfo(@PathVariable Long petID){
        return ResponseEntity.ok(petService.getPetInfo(petID));
    }

    /**
     * Checks if an adoption request exists for a pet and a pet adopter.
     * @param petID The ID of the pet.
     * @param petAdopterID The ID of the pet adopter.
     * @return ResponseEntity indicating the existence of the request.
     */
    @GetMapping("/pet/adopt/requestexists")
    public ResponseEntity<?> requestExists( @RequestParam("petID") Long petID, @RequestParam("petAdopterID") Long petAdopterID){
        boolean success = petAdopterService.requestExists(petID,petAdopterID);
        if(success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    /**
     * Registers a lost pet.
     * @param authorizationHeader The authorization header.
     * @param registerLostPetDto The details of the lost pet.
     * @return ResponseEntity containing the registered LostPet entity.
     */
    @PostMapping("/lostpet")
    public ResponseEntity<LostPet> registerLostPet(@Valid @RequestHeader("Authorization") String authorizationHeader, @RequestBody RegisterLostPetDto registerLostPetDto) {
        return ResponseEntity.ok(lostPetService.registerLostPet(authorizationHeader, registerLostPetDto));
    }

    /**
     * Searches for lost pets based on search criteria.
     * @param SearchPetDto The search criteria.
     * @return ResponseEntity containing the search results.
     */
    @PostMapping("/searchlostpet")
    public ResponseEntity<LostPetResponseDtoListDto> searchLostPet(@RequestBody SearchPetDto SearchPetDto) {
        return ResponseEntity.ok(lostPetService.searchLostPet(SearchPetDto));
    }

    /**
     * Retrieves lost pets by user ID.
     * @param userId The ID of the user.
     * @return ResponseEntity containing the list of lost pets.
     */
    @GetMapping("/lostpet/{userId}")
    public ResponseEntity<LostPetResponseDtoListDto> getLostPetBy(@PathVariable Long userId) {
        return ResponseEntity.ok(lostPetService.getLostPetListByUser(userId));
    }

    /**
     * Updates lost pet details.
     * @param lostPetDto The updated lost pet details.
     * @return ResponseEntity containing the result of the update operation.
     */
    @PostMapping("/lostpet/update")
    public ResponseEntity<?> updateLostPetDetails(@RequestBody LostPetDto lostPetDto) {
        return ResponseEntity.ok(lostPetService.updateLostPetDetails(lostPetDto));
    }

    /**
     * Retrieves pets in a shelter.
     * @param shelterID The ID of the shelter.
     * @return ResponseEntity containing the list of pets.
     */
    @GetMapping("/{shelterID}/pets")
    public ResponseEntity<List<PetDto>> getPetInShelter(@PathVariable Long shelterID) {
        return ResponseEntity.ok(shelterService.getPetsForShelter(shelterID));
    }

    /**
     * Retrieves all lost pets.
     * @return ResponseEntity containing a list of LostPetDto.
     */
    @GetMapping("/lostpets")
    public ResponseEntity<List<LostPetDto>> getAllLostpets() {
        List<LostPetDto> lostPetDtoList = lostPetService.getAllLostPets();
        return ResponseEntity.ok(lostPetDtoList);
    }
}
