package com.oscaresteve.rentboot.srv;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oscaresteve.rentboot.model.dto.rol.RolEdit;
import com.oscaresteve.rentboot.model.dto.rol.RolList;
import com.oscaresteve.rentboot.model.dto.rol.RolView;

public interface RolService {

  // Create
  RolView createRol(RolEdit rolEdit);

  // Read
  RolView getRolById(Long id);

  Page<RolList> getAllRoles(Pageable pageable);

  // Filtrado opcional
  List<RolList> findByNombreContaining(String nombre);

  // Update
  RolView updateRol(Long id, RolEdit rolEdit);

  // Delete
  void deleteRol(Long id);
}
