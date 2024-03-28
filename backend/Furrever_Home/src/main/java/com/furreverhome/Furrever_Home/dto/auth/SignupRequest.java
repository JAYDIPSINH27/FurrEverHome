package com.furreverhome.Furrever_Home.dto.auth;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "role")
@JsonSubTypes({@Type(value = ShelterSignupRequest.class, name = "shelter"),
        @Type(value = PetAdopterSignupRequest.class, name = "petadopter")})
public abstract class SignupRequest {

    private String email;

    private String password;

    private String role;

    private Integer checkRole;
}
