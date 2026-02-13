package com.oscaresteve.rentboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscaresteve.rentboot.model.db.AlquilerDb;

@Repository
public interface AlquilerRepository extends JpaRepository<AlquilerDb, Long> {

  //Buscar por cliente
  List<AlquilerDb> findByClienteId(Long clienteId);

  //Buscar por vehiculo
  List<AlquilerDb> findByVehiculoId(Long vehiculoId);
}
