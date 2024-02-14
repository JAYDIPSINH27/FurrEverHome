package com.furreverhome.Furrever_Home.dto;

import com.furreverhome.Furrever_Home.enums.Role;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String token;

    private String refreshToken;

    private Role userRole;

    private Long userId;

    private Boolean verified;

}