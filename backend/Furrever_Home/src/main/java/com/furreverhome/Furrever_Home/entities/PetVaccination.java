package com.furreverhome.Furrever_Home.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class PetVaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petVaccineID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petID", referencedColumnName = "petID", nullable = false)
    private Pet pet;

    private String vaccineName;

    private boolean vaccineGiven;

    private Date date;

}
