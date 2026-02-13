package com.oscaresteve.rentboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscaresteve.rentboot.model.db.ClienteDb;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteDb, Long>{
  
  //Buscar por email
  Optional<ClienteDb> findByEmail(String email);
}
