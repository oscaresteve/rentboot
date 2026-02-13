package com.oscaresteve.rentboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscaresteve.rentboot.model.db.RolDb;

@Repository
public interface RolRepository extends JpaRepository<RolDb, Long> {

  //Buscar por nombre
  Optional<RolDb> findByNombre(String nombre);
}
