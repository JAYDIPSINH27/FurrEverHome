package com.furreverhome.Furrever_Home.services.petadopterservices;

import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetAdoptionRequestDto;
import com.furreverhome.Furrever_Home.dto.petadopter.PetAdopterDto;
import com.furreverhome.Furrever_Home.dto.petadopter.*;

import java.util.List;

public interface PetAdopterService {
    List<ShelterResponseDto> getAllShelter();

    PetAdopterDto getPetAdopterDetailsById(Long userId);

    ShelterResponseDtoListDto searchShelter(SearchShelterDto searchShelterDto);

    PetResponseDtoListDto searchPet(SearchPetDto searchPetDto);

    GenericResponse adoptPetRequest(PetAdoptionRequestDto petAdoptionRequestDto);

    boolean requestExists(Long petID, Long petAdopterID);

}
