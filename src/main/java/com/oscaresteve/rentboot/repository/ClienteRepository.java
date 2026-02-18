package com.oscaresteve.rentboot.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscaresteve.rentboot.model.db.ClienteDb;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteDb, Long>{
  
  // Filtrado
  Optional<ClienteDb> findByEmail(String email);

  Page<ClienteDb> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

  Page<ClienteDb> findByEmailContainingIgnoreCase(String email, Pageable pageable);

  Page<ClienteDb> findByTelefonoContainingIgnoreCase(String telefono, Pageable pageable);
}
