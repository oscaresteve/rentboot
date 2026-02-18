package com.oscaresteve.rentboot.model.dto.alquiler;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlquilerCategoriaStats {

  private Long categoriaId;
  private String categoriaNombre;
  private Long totalAlquileres;
  private BigDecimal ingresoTotal;
  private Double ticketPromedio;
}
