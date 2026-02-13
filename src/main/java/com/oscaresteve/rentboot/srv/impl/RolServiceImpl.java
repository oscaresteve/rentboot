package com.oscaresteve.rentboot.srv.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oscaresteve.rentboot.model.db.RolDb;
import com.oscaresteve.rentboot.model.dto.rol.RolEdit;
import com.oscaresteve.rentboot.model.dto.rol.RolList;
import com.oscaresteve.rentboot.model.dto.rol.RolView;
import com.oscaresteve.rentboot.repository.RolRepository;
import com.oscaresteve.rentboot.srv.RolService;
import com.oscaresteve.rentboot.srv.mapper.RolMapper;

@Service
public class RolServiceImpl implements RolService {

  @Autowired
  private RolRepository rolRepository;

  private final RolMapper mapper = RolMapper.INSTANCE;

  // Create
  @Override
  public RolView createRol(RolEdit rolEdit) {
    RolDb rolDb = mapper.RolEditToRolDb(rolEdit);
    rolDb = rolRepository.save(rolDb);
    return mapper.RolDbToRolView(rolDb);
  }

  // Read
  @Override
  public RolView getRolById(Long id) {
    RolDb rolDb = rolRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    return mapper.RolDbToRolView(rolDb);
  }

  @Override
  public Page<RolList> getAllRoles(Pageable pageable) {
    Page<RolDb> page = rolRepository.findAll(pageable);
    return page.map(mapper::RolDbToRolList);
  }

  // Filtrado opcional
  @Override
  public List<RolList> findByNombreContaining(String nombre) {
    return rolRepository.findAll().stream()
      .filter(r -> r.getNombre().toLowerCase().contains(nombre.toLowerCase()))
      .map(mapper::RolDbToRolList)
      .collect(Collectors.toList());
  }

  // Update
  @Override
  public RolView updateRol(Long id, RolEdit rolEdit) {
    RolDb rolDb = rolRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    mapper.updateRolDbFromRolEdit(rolEdit, rolDb);
    rolDb = rolRepository.save(rolDb);
    return mapper.RolDbToRolView(rolDb);
  }

  // Delete
  @Override
  public void deleteRol(Long id) {
    if (!rolRepository.existsById(id)) {
      throw new RuntimeException("Rol no encontrado");
    }
    rolRepository.deleteById(id);
  }
}
