package com.furreverhome.Furrever_Home.dto.petadopter;

import com.furreverhome.Furrever_Home.entities.Shelter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PetResponseDto {
    private Long petId;

    private String breed;

    private String color;

    private String gender;

    private String type;

    private String petImage;

    private Integer age;

    private String shelterName;

    private String shelterCity;

    private String shelterContact;

    private boolean adopted;
}
