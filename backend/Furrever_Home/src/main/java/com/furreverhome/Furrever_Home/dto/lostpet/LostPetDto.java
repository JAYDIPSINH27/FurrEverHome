package com.furreverhome.Furrever_Home.dto.lostpet;

import com.furreverhome.Furrever_Home.entities.User;
import lombok.Data;

@Data
public class LostPetDto {
    private Long id;
    private String type;
    private String breed;
    private String colour;
    private String gender;
    private String petImage;
    private String phone;
    private String email;
    private Long userId;
}
