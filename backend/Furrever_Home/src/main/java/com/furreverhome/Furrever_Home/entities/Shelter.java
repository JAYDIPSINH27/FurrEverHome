package com.furreverhome.Furrever_Home.entities;

import com.furreverhome.Furrever_Home.dto.petadopter.ShelterResponseDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="shelter")
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long capacity;

    private String contact;

    @Lob
    @Column(length = 100000)
    private String imageBase64;


    @Lob
    @Column(length = 100000)
    private String license;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String address;

    private String city;

    private String country;

    private String zipcode;

    private boolean accepted;

    private boolean rejected;

    public ShelterResponseDto getShelterResponseDto() {
        ShelterResponseDto shelterResponseDto = new ShelterResponseDto();
        shelterResponseDto.setId(id);
        shelterResponseDto.setName(name);
        shelterResponseDto.setCapacity(capacity);
        shelterResponseDto.setAddress(address);
        shelterResponseDto.setCity(city);
        shelterResponseDto.setCountry(country);
        shelterResponseDto.setZipcode(zipcode);
        shelterResponseDto.setContact(contact);
        shelterResponseDto.setImage(imageBase64);
        shelterResponseDto.setLicense(license);
        shelterResponseDto.setEmail(user.getEmail());
        shelterResponseDto.setUser(user);
        shelterResponseDto.setVerified(user.getVerified());
        shelterResponseDto.setAccepted(accepted);
        shelterResponseDto.setRejected(rejected);
        return shelterResponseDto;
    }
}
