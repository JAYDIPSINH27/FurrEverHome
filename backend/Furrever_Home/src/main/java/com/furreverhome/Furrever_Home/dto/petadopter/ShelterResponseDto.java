package com.furreverhome.Furrever_Home.dto.petadopter;

import com.furreverhome.Furrever_Home.entities.User;
import lombok.Data;

@Data
public class ShelterResponseDto {
    private Long id;

    private String name;

    private String address;

    private String city;

    private String country;

    private String zipcode;

    private Long capacity;

    private String contact;

    private String email;

    private String image;

    private String license;

    private boolean verified;

    private boolean accepted;

    private boolean rejected;

    private User user;
}
