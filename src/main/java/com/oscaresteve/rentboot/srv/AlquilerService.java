package com.oscaresteve.rentboot.srv;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerEdit;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerList;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerView;

public interface AlquilerService {

  // Create
  AlquilerView createAlquiler(AlquilerEdit alquilerEdit);

  // Read
  AlquilerView getAlquilerById(Long id);

  Page<AlquilerList> getAllAlquileres(Pageable pageable);

  // Filtrado opcional
  List<AlquilerList> findByClienteId(Long clienteId);

  List<AlquilerList> findByVehiculoId(Long vehiculoId);

  // Update
  AlquilerView updateAlquiler(Long id, AlquilerEdit alquilerEdit);

  // Delete
  void deleteAlquiler(Long id);
}
