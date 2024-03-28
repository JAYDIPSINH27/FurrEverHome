package com.furreverhome.Furrever_Home.entities;

import com.furreverhome.Furrever_Home.dto.petadopter.PetResponseDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petID;
    private String type;
    private String breed;
    private String colour;
    private String gender;
    private Date birthdate;

    private Integer age;

    @Lob
    @Column(length = 100000)
    private String petImage;

    @Column(length = 1000)
    private String petMedicalHistory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shelterID", referencedColumnName = "id")
    private Shelter shelter;

    private boolean adopted;

    public PetResponseDto getPetResponseDto() {
        PetResponseDto petResponseDto = new PetResponseDto();

        petResponseDto.setPetId(petID);
        petResponseDto.setAge(age);
        petResponseDto.setBreed(breed);
        petResponseDto.setType(type);
        petResponseDto.setPetImage(petImage);
        petResponseDto.setShelterName(shelter.getName());
        petResponseDto.setShelterCity(shelter.getCity());
        petResponseDto.setShelterContact(shelter.getContact());
//        petResponseDto.setShelter(shelter);
        petResponseDto.setColor(colour);
        petResponseDto.setGender(gender);
        petResponseDto.setAdopted(adopted);

        return petResponseDto;
    }
}
