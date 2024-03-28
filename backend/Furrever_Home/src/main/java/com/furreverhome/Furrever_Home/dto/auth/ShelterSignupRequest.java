package com.furreverhome.Furrever_Home.dto.auth;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.furreverhome.Furrever_Home.dto.auth.SignupRequest;
import lombok.Data;

@Data
@JsonTypeName("shelter")
public class ShelterSignupRequest extends SignupRequest {
    private String name;

    private Long capacity;

    private String contact;

    private String imageBase64;

    private String license;

//    private LocalDateTime createdAt;

    private String message;

    private String address;

    private String city;

    private String country;

    private String zipcode;

}
