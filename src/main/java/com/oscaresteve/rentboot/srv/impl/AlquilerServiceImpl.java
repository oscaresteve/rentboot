package com.oscaresteve.rentboot.srv.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oscaresteve.rentboot.exception.EntityNotFoundException;
import com.oscaresteve.rentboot.exception.FiltroException;
import com.oscaresteve.rentboot.model.db.AlquilerDb;
import com.oscaresteve.rentboot.model.db.ClienteDb;
import com.oscaresteve.rentboot.model.db.VehiculoDb;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerCategoriaStats;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerClienteStats;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerEdit;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerList;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerVehiculoStats;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerView;
import com.oscaresteve.rentboot.repository.AlquilerRepository;
import com.oscaresteve.rentboot.repository.ClienteRepository;
import com.oscaresteve.rentboot.repository.VehiculoRepository;
import com.oscaresteve.rentboot.srv.AlquilerService;
import com.oscaresteve.rentboot.srv.factory.AlquilerFactory;
import com.oscaresteve.rentboot.srv.mapper.AlquilerMapper;
import com.oscaresteve.rentboot.srv.strategy.alquiler.AlquilerFiltroStrategyFactory;
import com.oscaresteve.rentboot.srv.strategy.alquiler.AlquilerFiltroTipo;

@Service
public class AlquilerServiceImpl implements AlquilerService {

  private final AlquilerRepository alquilerRepository;
  private final ClienteRepository clienteRepository;
  private final VehiculoRepository vehiculoRepository;
  private final AlquilerFactory alquilerFactory;
  private final AlquilerFiltroStrategyFactory filtroStrategyFactory;
  private final AlquilerMapper mapper;

  public AlquilerServiceImpl(
    AlquilerRepository alquilerRepository,
    ClienteRepository clienteRepository,
    VehiculoRepository vehiculoRepository,
    AlquilerFactory alquilerFactory,
    AlquilerFiltroStrategyFactory filtroStrategyFactory,
    AlquilerMapper mapper
  ) {
    this.alquilerRepository = alquilerRepository;
    this.clienteRepository = clienteRepository;
    this.vehiculoRepository = vehiculoRepository;
    this.alquilerFactory = alquilerFactory;
    this.filtroStrategyFactory = filtroStrategyFactory;
    this.mapper = mapper;
  }

  // Create
  @Override
  public AlquilerView createAlquiler(AlquilerEdit alquilerEdit) {
    ClienteDb clienteDb = clienteRepository.findById(alquilerEdit.getClienteId())
      .orElseThrow(() -> new EntityNotFoundException("CLIENTE_NOT_FOUND", "Cliente no encontrado"));
    VehiculoDb vehiculoDb = vehiculoRepository.findById(alquilerEdit.getVehiculoId())
      .orElseThrow(() -> new EntityNotFoundException("VEHICULO_NOT_FOUND", "Vehiculo no encontrado"));

    AlquilerDb alquilerDb = alquilerFactory.create(alquilerEdit, clienteDb, vehiculoDb);
    alquilerDb = alquilerRepository.save(alquilerDb);
    return mapper.AlquilerDbToAlquilerView(alquilerDb);
  }

  // Read
  @Override
  public AlquilerView getAlquilerById(Long id) {
    AlquilerDb alquilerDb = alquilerRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("ALQUILER_NOT_FOUND", "Alquiler no encontrado"));
    return mapper.AlquilerDbToAlquilerView(alquilerDb);
  }

  @Override
  public Page<AlquilerList> getAllAlquileres(Pageable pageable) {
    Page<AlquilerDb> page = alquilerRepository.findAll(pageable);
    return page.map(mapper::AlquilerDbToAlquilerList);
  }

  // Filtrado
  @Override
  public Page<AlquilerList> getAlquileresByClienteId(Long clienteId, Pageable pageable) {
    return getAlquileresFiltrados(AlquilerFiltroTipo.CLIENTE, clienteId, pageable);
  }

  @Override
  public Page<AlquilerList> getAlquileresByVehiculoId(Long vehiculoId, Pageable pageable) {
    return getAlquileresFiltrados(AlquilerFiltroTipo.VEHICULO, vehiculoId, pageable);
  }

  private Page<AlquilerList> getAlquileresFiltrados(AlquilerFiltroTipo filtroTipo, Long id, Pageable pageable) {
    Page<AlquilerDb> page = filtroStrategyFactory.getStrategy(filtroTipo).filtrar(id, pageable);
    return page.map(mapper::AlquilerDbToAlquilerList);
  }

  // Agregaciones
  @Override
  public List<AlquilerCategoriaStats> getStatsByCategoria() {
    return alquilerRepository.getStatsByCategoria();
  }

  @Override
  public List<AlquilerVehiculoStats> getTopVehiculos(int limit) {
    if (limit <= 0) {
      throw new FiltroException("FILTER_ERROR", "El parametro limit debe ser mayor que 0");
    }
    return alquilerRepository.getTopVehiculos(PageRequest.of(0, limit));
  }

  @Override
  public List<AlquilerClienteStats> getTopClientes(int limit) {
    if (limit <= 0) {
      throw new FiltroException("FILTER_ERROR", "El parametro limit debe ser mayor que 0");
    }
    return alquilerRepository.getTopClientes(PageRequest.of(0, limit));
  }

  // Update
  @Override
  public AlquilerView updateAlquiler(Long id, AlquilerEdit alquilerEdit) {
    AlquilerDb alquilerDb = alquilerRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("ALQUILER_NOT_FOUND", "Alquiler no encontrado"));
    ClienteDb clienteDb = clienteRepository.findById(alquilerEdit.getClienteId())
      .orElseThrow(() -> new EntityNotFoundException("CLIENTE_NOT_FOUND", "Cliente no encontrado"));
    VehiculoDb vehiculoDb = vehiculoRepository.findById(alquilerEdit.getVehiculoId())
      .orElseThrow(() -> new EntityNotFoundException("VEHICULO_NOT_FOUND", "Vehiculo no encontrado"));

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
      throw new EntityNotFoundException("ALQUILER_NOT_FOUND", "Alquiler no encontrado");
    }
    alquilerRepository.deleteById(id);
  }
}
