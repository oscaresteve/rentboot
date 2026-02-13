package com.oscaresteve.rentboot.srv;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oscaresteve.rentboot.model.dto.vehiculo.VehiculoEdit;
import com.oscaresteve.rentboot.model.dto.vehiculo.VehiculoList;
import com.oscaresteve.rentboot.model.dto.vehiculo.VehiculoView;

public interface VehiculoService {

  // Create
  VehiculoView createVehiculo(VehiculoEdit vehiculoEdit);

  // Read
  VehiculoView getVehiculoById(Long id);

  Page<VehiculoList> getAllVehiculos(Pageable pageable);

  // Filtrado opcional
  List<VehiculoList> findByDisponibleTrue();

  // Update
  VehiculoView updateVehiculo(Long id, VehiculoEdit vehiculoEdit);

  // Delete
  void deleteVehiculo(Long id);
}
