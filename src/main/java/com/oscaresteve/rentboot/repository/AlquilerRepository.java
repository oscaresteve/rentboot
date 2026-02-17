package com.oscaresteve.rentboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscaresteve.rentboot.model.db.AlquilerDb;

@Repository
public interface AlquilerRepository extends JpaRepository<AlquilerDb, Long> {

  //Buscar por id de cliente
  Page<AlquilerDb> findByClienteId(Long clienteId, Pageable pageable);

  //Buscar por id de vehiculo
  Page<AlquilerDb> findByVehiculoId(Long vehiculoId, Pageable pageable);
}
