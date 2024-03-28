package com.furreverhome.Furrever_Home.dto.lostpet;

import lombok.Data;

@Data
public class RegisterLostPetDto {
    private String type;
    private String breed;
    private String colour;
    private String gender;
    private String petImage;
    private String phone;
    private String email;
    private long user;
}
