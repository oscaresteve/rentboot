package com.oscaresteve.rentboot.model.dto.alquiler;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlquilerView {

  private Long id;
  private LocalDate fechaInicio;
  private LocalDate fechaFin;
  private BigDecimal precioTotal;
  private Long clienteId;
  private Long vehiculoId;
}
