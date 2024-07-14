package com.furreverhome.Furrever_Home.dto.user;

import com.furreverhome.Furrever_Home.validation.PasswordMatches;
import com.furreverhome.Furrever_Home.validation.ValidEmail;
import com.furreverhome.Furrever_Home.validation.ValidPassword;
import lombok.Data;

@Data
@PasswordMatches
public class PasswordDto {

  private String email;

  private String oldPassword;

  private String token;

  @ValidPassword
  private String newPassword;

  @ValidPassword
  private String verifyNewPassword;
}
