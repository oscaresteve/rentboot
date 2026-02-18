package com.oscaresteve.rentboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oscaresteve.rentboot.model.db.AlquilerDb;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerCategoriaStats;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerClienteStats;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerVehiculoStats;

@Repository
public interface AlquilerRepository extends JpaRepository<AlquilerDb, Long> {

  // Filtrado
  Page<AlquilerDb> findByClienteId(Long clienteId, Pageable pageable);

  Page<AlquilerDb> findByVehiculoId(Long vehiculoId, Pageable pageable);

  // Agregaciones
  @Query("""
      SELECT new com.oscaresteve.rentboot.model.dto.alquiler.AlquilerCategoriaStats(
        v.categoria.id,
        v.categoria.nombre,
        COUNT(a),
        SUM(a.precioTotal),
        AVG(a.precioTotal)
      )
      FROM AlquilerDb a
      JOIN a.vehiculo v
      GROUP BY v.categoria.id, v.categoria.nombre
      ORDER BY COUNT(a) DESC
      """)
  List<AlquilerCategoriaStats> getStatsByCategoria();

  @Query("""
      SELECT new com.oscaresteve.rentboot.model.dto.alquiler.AlquilerVehiculoStats(
        v.id,
        v.marca,
        v.modelo,
        v.matricula,
        COUNT(a),
        SUM(a.precioTotal)
      )
      FROM AlquilerDb a
      JOIN a.vehiculo v
      GROUP BY v.id, v.marca, v.modelo, v.matricula
      ORDER BY COUNT(a) DESC
      """)
  List<AlquilerVehiculoStats> getTopVehiculos(Pageable pageable);

  @Query("""
      SELECT new com.oscaresteve.rentboot.model.dto.alquiler.AlquilerClienteStats(
        c.id,
        c.nombre,
        c.email,
        COUNT(a),
        SUM(a.precioTotal),
        AVG(a.precioTotal)
      )
      FROM AlquilerDb a
      JOIN a.cliente c
      GROUP BY c.id, c.nombre, c.email
      ORDER BY COUNT(a) DESC
      """)
  List<AlquilerClienteStats> getTopClientes(Pageable pageable);
}
