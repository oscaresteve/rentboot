package com.oscaresteve.rentboot.srv.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oscaresteve.rentboot.model.db.CategoriaDb;
import com.oscaresteve.rentboot.model.db.VehiculoDb;
import com.oscaresteve.rentboot.model.dto.vehiculo.VehiculoEdit;
import com.oscaresteve.rentboot.model.dto.vehiculo.VehiculoList;
import com.oscaresteve.rentboot.model.dto.vehiculo.VehiculoView;
import com.oscaresteve.rentboot.repository.CategoriaRepository;
import com.oscaresteve.rentboot.repository.VehiculoRepository;
import com.oscaresteve.rentboot.srv.VehiculoService;
import com.oscaresteve.rentboot.srv.mapper.VehiculoMapper;

@Service
public class VehiculoServiceImpl implements VehiculoService {

  @Autowired
  private VehiculoRepository vehiculoRepository;

  @Autowired
  private CategoriaRepository categoriaRepository;

  private final VehiculoMapper mapper = VehiculoMapper.INSTANCE;

  // Create
  @Override
  public VehiculoView createVehiculo(VehiculoEdit vehiculoEdit) {
    CategoriaDb categoriaDb = categoriaRepository.findById(vehiculoEdit.getCategoriaId())
      .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

    VehiculoDb vehiculoDb = mapper.VehiculoEditToVehiculoDb(vehiculoEdit);
    vehiculoDb.setCategoria(categoriaDb);
    vehiculoDb = vehiculoRepository.save(vehiculoDb);
    return mapper.VehiculoDbToVehiculoView(vehiculoDb);
  }

  // Read
  @Override
  public VehiculoView getVehiculoById(Long id) {
    VehiculoDb vehiculoDb = vehiculoRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));
    return mapper.VehiculoDbToVehiculoView(vehiculoDb);
  }

  @Override
  public Page<VehiculoList> getAllVehiculos(Pageable pageable) {
    Page<VehiculoDb> page = vehiculoRepository.findAll(pageable);
    return page.map(mapper::VehiculoDbToVehiculoList);
  }

  // Filtrado opcional
  @Override
  public List<VehiculoList> findByDisponibleTrue() {
    return vehiculoRepository.findByDisponibleTrue().stream()
      .map(mapper::VehiculoDbToVehiculoList)
      .collect(Collectors.toList());
  }

  // Update
  @Override
  public VehiculoView updateVehiculo(Long id, VehiculoEdit vehiculoEdit) {
    VehiculoDb vehiculoDb = vehiculoRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));
    CategoriaDb categoriaDb = categoriaRepository.findById(vehiculoEdit.getCategoriaId())
      .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

    mapper.updateVehiculoDbFromVehiculoEdit(vehiculoEdit, vehiculoDb);
    vehiculoDb.setCategoria(categoriaDb);
    vehiculoDb = vehiculoRepository.save(vehiculoDb);
    return mapper.VehiculoDbToVehiculoView(vehiculoDb);
  }

  // Delete
  @Override
  public void deleteVehiculo(Long id) {
    if (!vehiculoRepository.existsById(id)) {
      throw new RuntimeException("Vehiculo no encontrado");
    }
    vehiculoRepository.deleteById(id);
  }
}
