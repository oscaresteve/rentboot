package com.oscaresteve.rentboot.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscaresteve.rentboot.model.db.RolDb;

@Repository
public interface RolRepository extends JpaRepository<RolDb, Long> {

  // Filtrado
  Optional<RolDb> findByNombre(String nombre);

  Page<RolDb> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
