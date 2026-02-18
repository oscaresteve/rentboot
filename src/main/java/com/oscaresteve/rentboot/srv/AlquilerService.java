package com.oscaresteve.rentboot.srv;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerCategoriaStats;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerClienteStats;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerEdit;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerList;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerVehiculoStats;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerView;

public interface AlquilerService {

  // Create
  AlquilerView createAlquiler(AlquilerEdit alquilerEdit);

  // Read
  AlquilerView getAlquilerById(Long id);

  Page<AlquilerList> getAllAlquileres(Pageable pageable);

  // Filtrado
  Page<AlquilerList> getAlquileresByClienteId(Long clienteId, Pageable pageable);

  Page<AlquilerList> getAlquileresByVehiculoId(Long vehiculoId, Pageable pageable);

  // Agregaciones
  List<AlquilerCategoriaStats> getStatsByCategoria();

  List<AlquilerVehiculoStats> getTopVehiculos(int limit);

  List<AlquilerClienteStats> getTopClientes(int limit);

  // Update
  AlquilerView updateAlquiler(Long id, AlquilerEdit alquilerEdit);

  // Delete
  void deleteAlquiler(Long id);
}
