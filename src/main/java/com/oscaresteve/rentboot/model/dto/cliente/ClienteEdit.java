package com.oscaresteve.rentboot.model.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEdit {

  @NotBlank(message = "El nombre es obligatorio")
  @Size(max = 100, message = "El nombre puede tener hasta 100 caracteres")
  private String nombre;

  @NotBlank(message = "El email es obligatorio")
  @Email(message = "Email no válido")
  private String email;

  @Size(max = 20, message = "El teléfono puede tener hasta 20 caracteres")
  private String telefono;
}