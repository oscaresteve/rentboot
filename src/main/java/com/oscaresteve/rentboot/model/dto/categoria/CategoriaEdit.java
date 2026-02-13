package com.oscaresteve.rentboot.model.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaEdit {

  @NotBlank(message = "El nombre es obligatorio")
  @Size(max = 50, message = "El nombre puede tener hasta 50 caracteres")
  private String nombre;

  @Size(max = 255, message = "La descripción puede tener hasta 255 caracteres")
  private String descripcion;
}
