package com.furreverhome.Furrever_Home.repository;

import com.furreverhome.Furrever_Home.entities.LostPet;
import com.furreverhome.Furrever_Home.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LostPetRepository extends JpaRepository<LostPet, Long> {
    List<LostPet> findByUser(User user);
}
