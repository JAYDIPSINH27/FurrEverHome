package com.furreverhome.Furrever_Home.entities;

import com.furreverhome.Furrever_Home.dto.petadopter.PetAdopterDto;
import com.furreverhome.Furrever_Home.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="petadopter")
public class PetAdopter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private String phone_number;

    private String address;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String city;

    private String country;

    private String zipcode;

    public PetAdopterDto getPetAdopterDto() {
        PetAdopterDto petAdopterDto = new PetAdopterDto();
        petAdopterDto.setId(id);
        petAdopterDto.setFirstname(firstname);
        petAdopterDto.setLastname(lastname);
        petAdopterDto.setUserRole(Role.PETADOPTER);
        petAdopterDto.setEmail(getUser().getEmail());
        petAdopterDto.setAddress(address);
        petAdopterDto.setCity(city);
        petAdopterDto.setZipcode(zipcode);

        return petAdopterDto;
    }

}
