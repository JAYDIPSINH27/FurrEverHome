package com.furreverhome.Furrever_Home.dto.Pet;

import com.furreverhome.Furrever_Home.entities.PetVaccination;
import com.furreverhome.Furrever_Home.entities.Shelter;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PetDto {
    private Long petID;
    private String type;
    private String breed;
    private String colour;
    private String gender;
    private Date birthdate;
    private String petImage;
    private String petMedicalHistory;
    private List<String> vaccineNameList;
    private Shelter shelter;
    private boolean adopted;
}
