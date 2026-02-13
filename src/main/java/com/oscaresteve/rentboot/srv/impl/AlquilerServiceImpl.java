package com.oscaresteve.rentboot.srv.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oscaresteve.rentboot.model.db.AlquilerDb;
import com.oscaresteve.rentboot.model.db.ClienteDb;
import com.oscaresteve.rentboot.model.db.VehiculoDb;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerEdit;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerList;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerView;
import com.oscaresteve.rentboot.repository.AlquilerRepository;
import com.oscaresteve.rentboot.repository.ClienteRepository;
import com.oscaresteve.rentboot.repository.VehiculoRepository;
import com.oscaresteve.rentboot.srv.AlquilerService;
import com.oscaresteve.rentboot.srv.mapper.AlquilerMapper;

@Service
public class AlquilerServiceImpl implements AlquilerService {

  @Autowired
  private AlquilerRepository alquilerRepository;

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private VehiculoRepository vehiculoRepository;

  private final AlquilerMapper mapper = AlquilerMapper.INSTANCE;

  // Create
  @Override
  public AlquilerView createAlquiler(AlquilerEdit alquilerEdit) {
    ClienteDb clienteDb = clienteRepository.findById(alquilerEdit.getClienteId())
      .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    VehiculoDb vehiculoDb = vehiculoRepository.findById(alquilerEdit.getVehiculoId())
      .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));

    AlquilerDb alquilerDb = mapper.AlquilerEditToAlquilerDb(alquilerEdit);
    alquilerDb.setCliente(clienteDb);
    alquilerDb.setVehiculo(vehiculoDb);
    alquilerDb = alquilerRepository.save(alquilerDb);
    return mapper.AlquilerDbToAlquilerView(alquilerDb);
  }

  // Read
  @Override
  public AlquilerView getAlquilerById(Long id) {
    AlquilerDb alquilerDb = alquilerRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Alquiler no encontrado"));
    return mapper.AlquilerDbToAlquilerView(alquilerDb);
  }

  @Override
  public Page<AlquilerList> getAllAlquileres(Pageable pageable) {
    Page<AlquilerDb> page = alquilerRepository.findAll(pageable);
    return page.map(mapper::AlquilerDbToAlquilerList);
  }

  // Filtrado opcional
  @Override
  public List<AlquilerList> findByClienteId(Long clienteId) {
    return alquilerRepository.findByClienteId(clienteId).stream()
      .map(mapper::AlquilerDbToAlquilerList)
      .collect(Collectors.toList());
  }

  @Override
  public List<AlquilerList> findByVehiculoId(Long vehiculoId) {
    return alquilerRepository.findByVehiculoId(vehiculoId).stream()
      .map(mapper::AlquilerDbToAlquilerList)
      .collect(Collectors.toList());
  }

  // Update
  @Override
  public AlquilerView updateAlquiler(Long id, AlquilerEdit alquilerEdit) {
    AlquilerDb alquilerDb = alquilerRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Alquiler no encontrado"));
    ClienteDb clienteDb = clienteRepository.findById(alquilerEdit.getClienteId())
      .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    VehiculoDb vehiculoDb = vehiculoRepository.findById(alquilerEdit.getVehiculoId())
      .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));

    mapper.updateAlquilerDbFromAlquilerEdit(alquilerEdit, alquilerDb);
    alquilerDb.setCliente(clienteDb);
    alquilerDb.setVehiculo(vehiculoDb);
    alquilerDb = alquilerRepository.save(alquilerDb);
    return mapper.AlquilerDbToAlquilerView(alquilerDb);
  }

  // Delete
  @Override
  public void deleteAlquiler(Long id) {
    if (!alquilerRepository.existsById(id)) {
      throw new RuntimeException("Alquiler no encontrado");
    }
    alquilerRepository.deleteById(id);
  }
}
