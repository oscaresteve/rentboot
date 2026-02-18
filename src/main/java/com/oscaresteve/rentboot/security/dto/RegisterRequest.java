package com.oscaresteve.rentboot.security.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

  @NotBlank(message = "El username es obligatorio")
  @Size(max = 100, message = "El username puede tener hasta 100 caracteres")
  private String username;

  @NotBlank(message = "La password es obligatoria")
  @Size(max = 255, message = "La password puede tener hasta 255 caracteres")
  private String password;

  private Boolean enabled = true;

  private Set<Long> rolIds;
}
