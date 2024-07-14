package com.furreverhome.Furrever_Home.dto.petadopter;

import lombok.Data;

@Data
public class SearchPetDto {
    private String type;

    private String breed;

    private Integer age;

    private String color;

    private String gender;
}
