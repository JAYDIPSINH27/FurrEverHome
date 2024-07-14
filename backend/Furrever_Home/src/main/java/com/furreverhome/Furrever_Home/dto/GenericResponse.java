package com.furreverhome.Furrever_Home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponse {
  String message;
  String error;

  public GenericResponse(String message) {
    this(message, null);
  }

}
