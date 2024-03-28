package com.furreverhome.Furrever_Home.repository;

import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.entities.PetVaccination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PetVaccinationRepository extends JpaRepository<PetVaccination, Long> {

    List<PetVaccination> findByPet(Pet pet);
    boolean existsByPetAndVaccineName(Pet pet, String vaccineName);

    Optional<PetVaccination> findFirstByPetAndDateGreaterThanOrderByDateAsc(Pet pet, Date today);

}
