package com.oscaresteve.rentboot.srv.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oscaresteve.rentboot.exception.DomainException;
import com.oscaresteve.rentboot.exception.EntityNotFoundException;
import com.oscaresteve.rentboot.model.db.RolDb;
import com.oscaresteve.rentboot.model.dto.rol.RolEdit;
import com.oscaresteve.rentboot.model.dto.rol.RolList;
import com.oscaresteve.rentboot.model.dto.rol.RolView;
import com.oscaresteve.rentboot.repository.RolRepository;
import com.oscaresteve.rentboot.srv.RolService;
import com.oscaresteve.rentboot.srv.mapper.RolMapper;

@Service
public class RolServiceImpl implements RolService {

  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  private static final String ROLE_USER = "ROLE_USER";

  @Autowired
  private RolRepository rolRepository;

  private final RolMapper mapper = RolMapper.INSTANCE;

  // Create
  @Override
  public RolView createRol(RolEdit rolEdit) {
    validateRoleName(rolEdit.getNombre());
    RolDb rolDb = mapper.RolEditToRolDb(rolEdit);
    rolDb = rolRepository.save(rolDb);
    return mapper.RolDbToRolView(rolDb);
  }

  // Read
  @Override
  public RolView getRolById(Long id) {
    RolDb rolDb = rolRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("ROL_NOT_FOUND", "Rol no encontrado"));
    return mapper.RolDbToRolView(rolDb);
  }

  @Override
  public Page<RolList> getAllRoles(Pageable pageable) {
    Page<RolDb> page = rolRepository.findAll(pageable);
    return page.map(mapper::RolDbToRolList);
  }

  // Filtrado
  @Override
  public Page<RolList> getRolesByNombre(String nombre, Pageable pageable) {
    Page<RolDb> page = rolRepository.findByNombreContainingIgnoreCase(nombre, pageable);
    return page.map(mapper::RolDbToRolList);
  }

  // Update
  @Override
  public RolView updateRol(Long id, RolEdit rolEdit) {
    RolDb rolDb = rolRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("ROL_NOT_FOUND", "Rol no encontrado"));
    if (isReservedRole(rolDb.getNombre())) {
      throw new DomainException("RESERVED_ROLE", "No se puede modificar un rol reservado del sistema");
    }
    validateRoleName(rolEdit.getNombre());
    mapper.updateRolDbFromRolEdit(rolEdit, rolDb);
    rolDb = rolRepository.save(rolDb);
    return mapper.RolDbToRolView(rolDb);
  }

  // Delete
  @Override
  public void deleteRol(Long id) {
    RolDb rolDb = rolRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("ROL_NOT_FOUND", "Rol no encontrado"));
    if (isReservedRole(rolDb.getNombre())) {
      throw new DomainException("RESERVED_ROLE", "No se puede eliminar un rol reservado del sistema");
    }
    rolRepository.deleteById(id);
  }

  private void validateRoleName(String roleName) {
    if (roleName == null || !roleName.startsWith("ROLE_")) {
      throw new DomainException("INVALID_ROLE_NAME", "El nombre del rol debe empezar por ROLE_");
    }
  }

  private boolean isReservedRole(String roleName) {
    return ROLE_ADMIN.equals(roleName) || ROLE_USER.equals(roleName);
  }
}
