package com.furreverhome.Furrever_Home.dto.lostpet;

import com.furreverhome.Furrever_Home.dto.petadopter.PetResponseDto;
import lombok.Data;

import java.util.List;
@Data
public class LostPetResponseDtoListDto {
    List<LostPetDto> lostPetDtoList;
}
