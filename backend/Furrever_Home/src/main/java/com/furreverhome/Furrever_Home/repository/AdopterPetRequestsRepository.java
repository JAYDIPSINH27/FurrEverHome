package com.furreverhome.Furrever_Home.repository;

import com.furreverhome.Furrever_Home.entities.AdopterPetRequests;
import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdopterPetRequestsRepository extends JpaRepository<AdopterPetRequests, Long> {
    @Query("SELECT ap.petAdopter FROM AdopterPetRequests ap WHERE ap.pet = :pet")
    List<PetAdopter> findPetAdoptersByPet(@Param("pet") Pet pet);


    boolean existsByPetAndPetAdopter(Pet pet, PetAdopter petAdopter);
}
