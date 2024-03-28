package com.furreverhome.Furrever_Home.services.petservice;

import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetDto;
import com.furreverhome.Furrever_Home.dto.Pet.PetVaccineDto;
import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.entities.PetVaccination;
import com.furreverhome.Furrever_Home.entities.PetVaccinationInfo;
import com.furreverhome.Furrever_Home.repository.PetRepository;
import com.furreverhome.Furrever_Home.repository.PetVaccinationInfoRepository;
import com.furreverhome.Furrever_Home.repository.PetVaccinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    @Autowired
    private final PetVaccinationRepository petVaccinationRepository;

    private final PetVaccinationInfoRepository petVaccinationInfoRepository;


    /**
     * Retrieves the information of a pet by its ID.
     *
     * @param petID The ID of the pet to retrieve information for.
     * @return A PetDto object containing the information of the pet.
     * @throws RuntimeException if the pet with the given ID is not found.
     */
    public PetDto getPetInfo(Long petID) {
        Optional<Pet> optionalPet = petRepository.findById(petID);
        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            return mapPetToDto(pet);
        } else {
            throw new RuntimeException("Pet not found.");
        }
    }

    /**
     * Adds vaccination details for a pet.
     *
     * @param petVaccineDto The vaccination details to be added.
     * @param petID         The ID of the pet to add vaccination details for.
     * @return A GenericResponse indicating the result of the operation.
     * @throws RuntimeException if the pet with the given ID is not found.
     */
    public GenericResponse addVaccinationDetails(PetVaccineDto petVaccineDto, Long petID) {
        Optional<Pet> optionalPet = petRepository.findById(petID);
        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            PetVaccination petVaccination = new PetVaccination();
            if (petVaccinationRepository.existsByPetAndVaccineName(pet, petVaccineDto.getVaccineName())) {
                return new GenericResponse("already present");
            } else {
                petVaccination.setPet(pet);
                petVaccination.setVaccineName(petVaccineDto.getVaccineName());
                petVaccination.setDate(petVaccineDto.getDate());
                petVaccination.setVaccineGiven(petVaccineDto.isVaccineGiven());
                petVaccinationRepository.save(petVaccination);
                updatePetVaccinationNotificationInfo(pet);
                return new GenericResponse("Vaccination added.");
            }
        } else {
            throw new RuntimeException("Pet with petID " + petID + " not found.");
        }
    }

    /**
     * Updates the pet vaccination notification information based on the next vaccination date.
     * If a pet vaccination is found with a date greater than today's date, it updates the next vaccination
     * date in the PetVaccinationInfo entity.
     *
     * @param pet The pet for which vaccination notification information is to be updated.
     */
    private void updatePetVaccinationNotificationInfo(Pet pet) {
        Date today = new Date();
        Optional<PetVaccination> petVaccination = petVaccinationRepository.findFirstByPetAndDateGreaterThanOrderByDateAsc(pet, today);

        if (petVaccination.isPresent()) {
            PetVaccination nextVaccination = petVaccination.get();
            PetVaccinationInfo petVaccinationInfo = petVaccinationInfoRepository.findById(pet.getPetID())
                    .orElse(new PetVaccinationInfo()); // Create a new one if not found

            petVaccinationInfo.setPetID(pet.getPetID()); // This is redundant for an update, but necessary for an insert
            LocalDate nextVaccinationDate = nextVaccination.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            petVaccinationInfo.setLastVaccinationDate(petVaccinationInfo.getLastVaccinationDate());
            petVaccinationInfo.setNextVaccinationDate(nextVaccinationDate);
            petVaccinationInfoRepository.save(petVaccinationInfo);
        }
    }
    //---------------------------UTILS----------------------------

    /**
     * Maps a Pet entity to a PetDto object.
     *
     * @param pet The Pet entity to map.
     * @return A PetDto object containing the mapped information.
     */
    private PetDto mapPetToDto(Pet pet) {
        PetDto petDto = new PetDto();
        petDto.setPetID(pet.getPetID());
        petDto.setType(pet.getType());
        petDto.setBreed(pet.getBreed());
        petDto.setColour(pet.getColour());
        petDto.setGender(pet.getGender());
        petDto.setBirthdate(pet.getBirthdate());
        petDto.setPetImage(pet.getPetImage());
        petDto.setPetMedicalHistory(pet.getPetMedicalHistory());
        List<PetVaccination> petVaccinationList = petVaccinationRepository.findByPet(pet);
        List<String> vaccineNameList = new ArrayList<>();
        for (PetVaccination petVaccine : petVaccinationList) {
            vaccineNameList.add(petVaccine.getVaccineName());
        }
        petDto.setVaccineNameList(vaccineNameList);
        petDto.setShelter(pet.getShelter());
        petDto.setAdopted(pet.isAdopted());
        return petDto;
    }

//    private PetResponseDto mapPetToDto(Pet pet) {
//        PetResponseDto petResponseDto = new PetResponseDto();
//        petResponseDto.setPetId(pet.getPetID());
//        petResponseDto.setType(pet.getType());
//        petResponseDto.setBreed(pet.getBreed());
//        petResponseDto.setColor(pet.getColour());
//        petResponseDto.setGender(pet.getGender());
//        petResponseDto.setBirthdate(pet.getBirthdate());
//        petResponseDto.setPetImage(pet.getPetImage());
//        petResponseDto.setShelterName(shelter.getName());
//        petResponseDto.setShelterCity(shelter.getCity());
//        petResponseDto.setShelterContact(shelter.getContact());
//        petResponseDto.setAdopted(pet.isAdopted());
//        return petResponseDto;
//    }
}
