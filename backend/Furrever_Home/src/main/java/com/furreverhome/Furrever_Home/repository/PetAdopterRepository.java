package com.furreverhome.Furrever_Home.repository;

import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetAdopterRepository extends JpaRepository<PetAdopter, Long> {
    Optional<PetAdopter> findByUser(User user);
    Optional<PetAdopter> findByUserId(Long userId);
}
