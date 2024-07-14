package com.furreverhome.Furrever_Home.dto.profile;

public record UpdateUserProfileResponseDto(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        String address,
        String city, String country, String zipcode) {
}
