package com.oscaresteve.rentboot.srv.strategy.alquiler;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.oscaresteve.rentboot.exception.FiltroException;

@Component
public class AlquilerFiltroStrategyFactory {

  private final Map<AlquilerFiltroTipo, AlquilerFiltroStrategy> strategies;

  public AlquilerFiltroStrategyFactory(List<AlquilerFiltroStrategy> strategyList) {
    this.strategies = new EnumMap<>(AlquilerFiltroTipo.class);
    strategyList.forEach(strategy -> strategies.put(strategy.getTipo(), strategy));
  }

  public AlquilerFiltroStrategy getStrategy(AlquilerFiltroTipo tipo) {
    AlquilerFiltroStrategy strategy = strategies.get(tipo);
    if (strategy == null) {
      throw new FiltroException("FILTER_ERROR", "No existe estrategia para el filtro solicitado");
    }
    return strategy;
  }
}
