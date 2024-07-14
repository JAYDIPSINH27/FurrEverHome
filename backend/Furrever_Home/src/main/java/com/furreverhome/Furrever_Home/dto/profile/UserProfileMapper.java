package com.furreverhome.Furrever_Home.dto.profile;

import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.Shelter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class UserProfileMapper {
    public Shelter toShelterEntity(UpdateShelterProfileRequestDto dto, Shelter shelter) {
        shelter.setName(dto.name());
        shelter.setAddress(dto.address());
        shelter.setCity(dto.city());
        shelter.setCountry(dto.country());
        shelter.setZipcode(dto.zipcode());
        shelter.setCapacity(dto.capacity());
        shelter.setContact(dto.contact());
        shelter.setImageBase64(dto.imageBase64());
        shelter.setLicense(dto.license());
        return shelter;
    }

    public UpdateShelterProfileResponseDto toUpdateShelterProfileResponseDto(Shelter shelter) {
        return new UpdateShelterProfileResponseDto(
                shelter.getUser().getId(),
                shelter.getName(),
                shelter.getAddress(),
                shelter.getCity(),
                shelter.getZipcode(),
                shelter.getCapacity(),
                shelter.getContact(),
                shelter.getImageBase64(),
                shelter.getLicense(),
                shelter.getCountry()
        );
    }

    public PetAdopter toPetAdopterEntity(UpdateUserProfileRequestDto dto, PetAdopter petAdopter) {
        petAdopter.setFirstname(dto.firstName());
        petAdopter.setLastname(dto.lastName());
        petAdopter.setPhone_number(dto.phoneNumber());
        petAdopter.setAddress(dto.address());
        petAdopter.setCity(dto.city());
        petAdopter.setCountry(dto.country());
        petAdopter.setZipcode(dto.zipcode());
        return petAdopter;
    }

    public UpdateUserProfileResponseDto toUpdateUserProfileResponseDto(PetAdopter petAdopter) {
        return new UpdateUserProfileResponseDto(
                petAdopter.getUser().getId(),
                petAdopter.getFirstname(),
                petAdopter.getLastname(),
                petAdopter.getPhone_number(),
                petAdopter.getAddress(),
                petAdopter.getCity(),
                petAdopter.getCountry(),
                petAdopter.getZipcode()
        );
    }
}
