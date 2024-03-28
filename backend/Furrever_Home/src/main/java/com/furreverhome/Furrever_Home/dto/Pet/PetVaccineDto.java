package com.furreverhome.Furrever_Home.dto.Pet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.furreverhome.Furrever_Home.entities.Pet;
import lombok.Data;

import java.util.Date;

@Data
public class PetVaccineDto {

    private Long petVaccineID;

    private String vaccineName;

    @JsonProperty("vaccineGiven")
    private boolean vaccineGiven;

    private Date date;
}
