package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.dto.petadopter.ShelterResponseDto;
import com.furreverhome.Furrever_Home.services.adminservices.AdminService;
import com.furreverhome.Furrever_Home.services.petadopterservices.PetAdopterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final PetAdopterService petAdopterService;

    /**
     * Retrieves all shelters.
     * @return ResponseEntity containing a list of ShelterResponseDto objects.
     */
    @GetMapping("/shelters")
    public ResponseEntity<List<ShelterResponseDto>> getAllShelters() {
        List<ShelterResponseDto> shelterResponseDtoList = petAdopterService.getAllShelter();
        return ResponseEntity.ok(shelterResponseDtoList);
    }

    /**
     * Changes the verification status of a shelter.
     * @param email The email of the shelter to modify.
     * @param status The new verification status.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    @GetMapping("/shelter/{email}/{status}")
    public ResponseEntity<?> changeVerifiedStatus(@PathVariable String email, @PathVariable String status) {
        System.out.println(email);
        System.out.println(status);
        boolean success = adminService.changeVerifiedStatus(email, status);
        if(success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
