package com.oscaresteve.rentboot.model.dto.vehiculo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoList {

  private Long id;
  private String marca;
  private String modelo;
  private String matricula;
  private BigDecimal precioPorDia;
  private Boolean disponible;
  private Long categoriaId;
}
