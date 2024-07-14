package com.furreverhome.Furrever_Home.dto.profile;

public record UpdateShelterProfileRequestDto(
        String name,
        String address,
        String city,
        String country,
        String zipcode,
        Long capacity,
        String contact,
        String imageBase64,
        String license
) {
}
