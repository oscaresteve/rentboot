package com.oscaresteve.rentboot.model.dto.vehiculo;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoEdit {

  @NotBlank(message = "La marca es obligatoria")
  @Size(max = 100, message = "La marca puede tener hasta 100 caracteres")
  private String marca;

  @NotBlank(message = "El modelo es obligatorio")
  @Size(max = 100, message = "El modelo puede tener hasta 100 caracteres")
  private String modelo;

  @NotBlank(message = "La matrícula es obligatoria")
  @Size(max = 20, message = "La matrícula puede tener hasta 20 caracteres")
  private String matricula;

  @NotNull(message = "El precio por día es obligatorio")
  @DecimalMin(value = "0.0", inclusive = false, message = "El precio por día debe ser mayor que 0")
  private BigDecimal precioPorDia;

  @NotNull(message = "El estado de disponibilidad es obligatorio")
  private Boolean disponible;

  @NotNull(message = "La categoría es obligatoria")
  private Long categoriaId;
}
