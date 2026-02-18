package com.oscaresteve.rentboot.srv.factory;

import org.springframework.stereotype.Component;

import com.oscaresteve.rentboot.model.db.AlquilerDb;
import com.oscaresteve.rentboot.model.db.ClienteDb;
import com.oscaresteve.rentboot.model.db.VehiculoDb;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerEdit;

@Component
public class AlquilerFactory {

  public AlquilerDb create(AlquilerEdit alquilerEdit, ClienteDb clienteDb, VehiculoDb vehiculoDb) {
    return AlquilerDb.builder()
      .fechaInicio(alquilerEdit.getFechaInicio())
      .fechaFin(alquilerEdit.getFechaFin())
      .precioTotal(alquilerEdit.getPrecioTotal())
      .cliente(clienteDb)
      .vehiculo(vehiculoDb)
      .build();
  }
}
