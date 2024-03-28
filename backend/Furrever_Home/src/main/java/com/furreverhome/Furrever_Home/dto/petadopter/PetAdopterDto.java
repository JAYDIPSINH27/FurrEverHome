package com.furreverhome.Furrever_Home.dto.petadopter;

import com.furreverhome.Furrever_Home.enums.Role;
import lombok.Data;

@Data
public class PetAdopterDto {
    private Long id;

    private String firstname;

    private String email;

    private Role userRole;

    private String city;

    private String address;

    private String zipcode;

    private String lastname;

}
