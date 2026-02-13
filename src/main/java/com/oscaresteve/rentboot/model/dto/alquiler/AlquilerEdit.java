package com.oscaresteve.rentboot.model.dto.alquiler;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlquilerEdit {

  @NotNull(message = "La fecha de inicio es obligatoria")
  private LocalDate fechaInicio;

  @NotNull(message = "La fecha de fin es obligatoria")
  private LocalDate fechaFin;

  @NotNull(message = "El precio total es obligatorio")
  @DecimalMin(value = "0.0", inclusive = false, message = "El precio total debe ser mayor que 0")
  private BigDecimal precioTotal;

  @NotNull(message = "El cliente es obligatorio")
  private Long clienteId;

  @NotNull(message = "El vehículo es obligatorio")
  private Long vehiculoId;
}
