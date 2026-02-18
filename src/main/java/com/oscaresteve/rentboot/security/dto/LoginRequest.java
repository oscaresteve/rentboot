package com.oscaresteve.rentboot.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

  @NotBlank(message = "El username es obligatorio")
  private String username;

  @NotBlank(message = "La password es obligatoria")
  private String password;
}
