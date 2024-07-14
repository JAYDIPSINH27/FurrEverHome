package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetAdoptionRequestResponseDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetVaccineDto;
import com.furreverhome.Furrever_Home.dto.petadopter.ShelterResponseDto;
import com.furreverhome.Furrever_Home.dto.shelter.RegisterPetRequest;
import com.furreverhome.Furrever_Home.services.petservice.PetService;
import com.furreverhome.Furrever_Home.services.shelterService.ShelterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelter")
@RequiredArgsConstructor
public class ShelterController {

    private final ShelterServiceImpl shelterService;
    private final PetService petService;

    /**
     * Registers a new pet in the shelter.
     *
     * @param registerPetRequest The request containing pet details.
     * @return ResponseEntity containing the registered PetDto.
     */
    @PostMapping("/registerPet")
    public ResponseEntity<PetDto> registerPetInShelter(@RequestBody RegisterPetRequest registerPetRequest){
        return ResponseEntity.ok(shelterService.registerPet(registerPetRequest));
    }

    /**
     * Edits an existing pet in the shelter.
     *
     * @param petID The ID of the pet to edit.
     * @param registerPetRequest The request containing updated pet details.
     * @return ResponseEntity containing the updated PetDto.
     */
    @PostMapping("/editPet/{petID}")
    public ResponseEntity<PetDto> editPetInShelter(@PathVariable Long petID, @RequestBody RegisterPetRequest registerPetRequest){
        return ResponseEntity.ok(shelterService.editPet(petID,registerPetRequest));
    }

    /**
     * Deletes a pet from the shelter.
     *
     * @param petID The ID of the pet to delete.
     * @return ResponseEntity containing a GenericResponse indicating success or failure.
     */
    @DeleteMapping("/deletePet/{petID}")
    public ResponseEntity<GenericResponse> deletePetInShelter(@PathVariable Long petID){
        return ResponseEntity.ok(shelterService.deletePet(petID));
    }

    /**
     * Retrieves all pets in the shelter by shelter ID.
     *
     * @param shelterID The ID of the shelter.
     * @return ResponseEntity containing a list of PetDto representing pets in the shelter.
     */
    @GetMapping("/{shelterID}/pets")
    public ResponseEntity<List<PetDto>> getPetInShelter(@PathVariable Long shelterID){
        return ResponseEntity.ok(shelterService.getPetsForShelter(shelterID));
    }

    /**
     * Changes the adopted status of a pet.
     *
     * @param petId The ID of the pet to update.
     * @param status The new adoption status.
     * @return ResponseEntity indicating success or failure.
     */
    @GetMapping("/shelter/{petId}/{status}")
    public ResponseEntity<?> changeAdoptedStatus(@PathVariable Long petId, @PathVariable String status) {
        boolean success = shelterService.changeAdoptedStatus(petId, status);
        if(success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    /**
     * Retrieves information about a specific pet by its ID.
     *
     * @param petID The ID of the pet.
     * @return ResponseEntity containing the PetDto representing the pet information.
     */
    @GetMapping("/{petID}")
    public ResponseEntity<PetDto> getPetInfo(@PathVariable Long petID){
        return ResponseEntity.ok(petService.getPetInfo(petID));
    }

    /**
     * Retrieves adoption requests for a specific pet by its ID.
     *
     * @param petID The ID of the pet.
     * @return ResponseEntity containing PetAdoptionRequestResponseDto representing the adoption requests.
     */
    @GetMapping("/{petID}/adoptionrequests")
    public ResponseEntity<PetAdoptionRequestResponseDto> getPetAdoptionRequests(@PathVariable Long petID){
        return ResponseEntity.ok(shelterService.getPetAdoptionRequests(petID));
    }

    /**
     * Adds a new vaccine for a pet.
     *
     * @param petID The ID of the pet.
     * @param petVaccineDto The request containing vaccine details.
     * @return ResponseEntity containing a GenericResponse indicating success or failure.
     */
    @PostMapping("/{petID}/addvaccine")
    public ResponseEntity<GenericResponse> addVaccine(@RequestBody PetVaccineDto petVaccineDto , @PathVariable Long petID){
        return ResponseEntity.ok(petService.addVaccinationDetails(petVaccineDto, petID));
    }

    /**
     * Retrieves shelter information by user ID.
     *
     * @param userId The ID of the user.
     * @return ResponseEntity containing the ShelterResponseDto representing shelter details.
     */
    @GetMapping("/single/{userId}")
    public ResponseEntity<ShelterResponseDto> getShelterByUser (@PathVariable Long userId) {
        ShelterResponseDto shelterResponseDto = shelterService.getShelterDetailsById(userId);
        if(shelterResponseDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(shelterResponseDto);
    }
}
