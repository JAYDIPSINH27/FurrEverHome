package com.furreverhome.Furrever_Home;

import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.entities.PetVaccinationInfo;
import com.furreverhome.Furrever_Home.entities.Shelter;
import com.furreverhome.Furrever_Home.entities.User;

import java.time.LocalDate;
import java.util.Date;

public class PetDataGenerator {

    /**
     * Creates a mock PetVaccinationInfo object with the provided parameters.
     *
     * @param petId               The ID of the pet.
     * @param type                The type of the pet.
     * @param breed               The breed of the pet.
     * @param lastVaccinationDate The date of the last vaccination.
     * @param nextVaccinationDate The date of the next vaccination.
     * @return A PetVaccinationInfo object with mock data.
     */
    public static PetVaccinationInfo createMockPetVaccinationInfo(Long petId, String type, String breed, LocalDate lastVaccinationDate, LocalDate nextVaccinationDate) {
        Pet pet = new Pet();
        pet.setPetID(petId);
        pet.setType(type);
        pet.setBreed(breed);
        pet.setColour("Black");
        pet.setGender("Male");
        pet.setBirthdate(new Date());
        pet.setAge(3);
        pet.setAdopted(false);

        Shelter shelter = new Shelter();
        shelter.setUser(new User()); // Assuming User is a class that contains an email field
        shelter.getUser().setEmail("test@example.com"); // Ensure this is set
        pet.setShelter(shelter);


        PetVaccinationInfo petVaccinationInfo = new PetVaccinationInfo();
        petVaccinationInfo.setPetID(petId);
        petVaccinationInfo.setPet(pet);
        petVaccinationInfo.setLastVaccinationDate(lastVaccinationDate);
        petVaccinationInfo.setNextVaccinationDate(nextVaccinationDate);

        return petVaccinationInfo;
    }
}
