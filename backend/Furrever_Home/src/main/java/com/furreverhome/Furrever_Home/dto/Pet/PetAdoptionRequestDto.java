package com.furreverhome.Furrever_Home.dto.Pet;

import lombok.Data;

@Data
public class PetAdoptionRequestDto {
    private Long petID;
    private Long petAdopterID;
}
