package com.furreverhome.Furrever_Home.repository;

import com.furreverhome.Furrever_Home.entities.PetVaccinationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PetVaccinationInfoRepository extends JpaRepository<PetVaccinationInfo, Long> {
    @Query("SELECT pvi FROM pet_vaccination_info pvi WHERE pvi.nextVaccinationDate BETWEEN :startDate AND :endDate ")
    List<PetVaccinationInfo> findAllWithNextVaccinationDueBetween(LocalDate startDate, LocalDate endDate);
}
