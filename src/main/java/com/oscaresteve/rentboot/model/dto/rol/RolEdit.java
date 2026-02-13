package com.oscaresteve.rentboot.model.dto.rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolEdit {

  @NotBlank(message = "El nombre es obligatorio")
  @Size(max = 50, message = "El nombre puede tener hasta 50 caracteres")
  private String nombre;
}
