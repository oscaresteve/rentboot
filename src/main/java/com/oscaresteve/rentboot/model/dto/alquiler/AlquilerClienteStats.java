package com.oscaresteve.rentboot.model.dto.alquiler;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlquilerClienteStats {

  private Long clienteId;
  private String nombre;
  private String email;
  private Long totalAlquileres;
  private BigDecimal gastoTotal;
  private Double gastoPromedio;
}
