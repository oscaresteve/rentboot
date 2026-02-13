package com.oscaresteve.rentboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oscaresteve.rentboot.model.db.UsuarioDb;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioDb, Long> {

  //Buscar por username
  Optional<UsuarioDb> findByUsername(String username);
}