package com.furreverhome.Furrever_Home.dto.profile;

public record UpdateShelterProfileResponseDto(
        Long id,
        String name,
        String address,
        String city,
        String zipcode,
        Long capacity,
        String contact,
        String imageBase64,
        String license,
        String country) {
}
