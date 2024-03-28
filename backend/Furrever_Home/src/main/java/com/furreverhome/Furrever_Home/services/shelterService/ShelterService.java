package com.furreverhome.Furrever_Home.services.shelterService;

import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.petadopter.ShelterResponseDto;
import com.furreverhome.Furrever_Home.dto.shelter.RegisterPetRequest;

import java.util.List;

public interface ShelterService {
    PetDto registerPet(RegisterPetRequest registerPetRequest);
    boolean changeAdoptedStatus(Long petId, String status);
    List<PetDto> getPetsForShelter(Long shelterID);
    ShelterResponseDto getShelterDetailsById(Long userId);
}
