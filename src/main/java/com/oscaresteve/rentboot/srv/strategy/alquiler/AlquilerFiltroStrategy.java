package com.oscaresteve.rentboot.srv.strategy.alquiler;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oscaresteve.rentboot.model.db.AlquilerDb;

public interface AlquilerFiltroStrategy {

  AlquilerFiltroTipo getTipo();

  Page<AlquilerDb> filtrar(Long id, Pageable pageable);
}
