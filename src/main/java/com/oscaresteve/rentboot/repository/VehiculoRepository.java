package com.oscaresteve.rentboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscaresteve.rentboot.model.db.VehiculoDb;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoDb, Long> {

  //Buscar por matricula
  Optional<VehiculoDb> findByMatricula(String matricula);

  //Buscar por disponibilidad
  List<VehiculoDb> findByDisponibleTrue();
}