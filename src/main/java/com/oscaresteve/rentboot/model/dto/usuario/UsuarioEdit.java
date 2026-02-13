package com.oscaresteve.rentboot.model.dto.usuario;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEdit {

  @NotBlank(message = "El username es obligatorio")
  @Size(max = 100, message = "El username puede tener hasta 100 caracteres")
  private String username;

  @NotBlank(message = "La contraseña es obligatoria")
  @Size(max = 255, message = "La contraseña puede tener hasta 255 caracteres")
  private String password;

  @NotNull(message = "El estado enabled es obligatorio")
  private Boolean enabled;

  @NotNull(message = "Los roles son obligatorios")
  private Set<Long> rolIds;
}
