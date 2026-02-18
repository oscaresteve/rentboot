package com.oscaresteve.rentboot.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscaresteve.rentboot.model.db.CategoriaDb;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaDb, Long> {

  // Filtrado
  Optional<CategoriaDb> findByNombre(String nombre);

  Page<CategoriaDb> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
