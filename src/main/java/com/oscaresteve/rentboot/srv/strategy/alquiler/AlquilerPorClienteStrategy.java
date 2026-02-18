package com.oscaresteve.rentboot.srv.strategy.alquiler;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.oscaresteve.rentboot.model.db.AlquilerDb;
import com.oscaresteve.rentboot.repository.AlquilerRepository;

@Component
public class AlquilerPorClienteStrategy implements AlquilerFiltroStrategy {

  private final AlquilerRepository alquilerRepository;

  public AlquilerPorClienteStrategy(AlquilerRepository alquilerRepository) {
    this.alquilerRepository = alquilerRepository;
  }

  @Override
  public AlquilerFiltroTipo getTipo() {
    return AlquilerFiltroTipo.CLIENTE;
  }

  @Override
  public Page<AlquilerDb> filtrar(Long id, Pageable pageable) {
    return alquilerRepository.findByClienteId(id, pageable);
  }
}
