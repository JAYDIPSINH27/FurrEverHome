package com.furreverhome.Furrever_Home.dto.shelter;

import com.furreverhome.Furrever_Home.entities.Shelter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class RegisterPetRequest {
    private String type;
    private String breed;
    private String colour;
    private String gender;
    private Date birthdate;
    private String petImage;
    private String petMedicalHistory;
    private long shelter;
    private boolean adopted;
}
