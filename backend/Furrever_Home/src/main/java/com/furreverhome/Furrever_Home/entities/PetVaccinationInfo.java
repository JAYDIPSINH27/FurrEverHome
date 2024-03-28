package com.furreverhome.Furrever_Home.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "pet_vaccination_info")
@Data
public class PetVaccinationInfo {
    @Id
    private Long petID;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "petID")
    private Pet pet;
    private LocalDate lastVaccinationDate;
    private LocalDate nextVaccinationDate;
}
