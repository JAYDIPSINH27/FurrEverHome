package com.furreverhome.Furrever_Home.services.petservice;

import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetVaccineDto;

public interface PetService {

    PetDto getPetInfo(Long petID);

    GenericResponse addVaccinationDetails(PetVaccineDto petVaccineDto, Long petID);
}
