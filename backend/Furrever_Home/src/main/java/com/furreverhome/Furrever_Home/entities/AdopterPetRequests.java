package com.furreverhome.Furrever_Home.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class AdopterPetRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petID", referencedColumnName = "petID", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adopterID", referencedColumnName = "id", nullable = false)
    private PetAdopter petAdopter;
}