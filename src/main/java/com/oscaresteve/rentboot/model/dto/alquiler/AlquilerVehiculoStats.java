package com.oscaresteve.rentboot.model.dto.alquiler;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlquilerVehiculoStats {

  private Long vehiculoId;
  private String marca;
  private String modelo;
  private String matricula;
  private Long totalAlquileres;
  private BigDecimal ingresoTotal;
}
