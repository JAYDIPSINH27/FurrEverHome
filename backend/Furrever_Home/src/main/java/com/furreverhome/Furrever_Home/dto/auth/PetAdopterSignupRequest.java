package com.furreverhome.Furrever_Home.dto.auth;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName("petadopter")
public class PetAdopterSignupRequest extends SignupRequest {

    private String firstName;

    private String lastName;

    private String phone_number;

    private String address;

    private String city;

    private String country;

    private String zipcode;
}
