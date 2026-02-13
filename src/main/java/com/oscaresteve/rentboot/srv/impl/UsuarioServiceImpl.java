package com.oscaresteve.rentboot.srv.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oscaresteve.rentboot.model.db.RolDb;
import com.oscaresteve.rentboot.model.db.UsuarioDb;
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioEdit;
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioList;
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioView;
import com.oscaresteve.rentboot.repository.RolRepository;
import com.oscaresteve.rentboot.repository.UsuarioRepository;
import com.oscaresteve.rentboot.srv.UsuarioService;
import com.oscaresteve.rentboot.srv.mapper.UsuarioMapper;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private RolRepository rolRepository;

  private final UsuarioMapper mapper = UsuarioMapper.INSTANCE;

  // Create
  @Override
  public UsuarioView createUsuario(UsuarioEdit usuarioEdit) {
    Set<RolDb> roles = resolveRoles(usuarioEdit.getRolIds());

    UsuarioDb usuarioDb = mapper.UsuarioEditToUsuarioDb(usuarioEdit);
    usuarioDb.setRoles(roles);
    usuarioDb = usuarioRepository.save(usuarioDb);
    return mapper.UsuarioDbToUsuarioView(usuarioDb);
  }

  // Read
  @Override
  public UsuarioView getUsuarioById(Long id) {
    UsuarioDb usuarioDb = usuarioRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    return mapper.UsuarioDbToUsuarioView(usuarioDb);
  }

  @Override
  public Page<UsuarioList> getAllUsuarios(Pageable pageable) {
    Page<UsuarioDb> page = usuarioRepository.findAll(pageable);
    return page.map(mapper::UsuarioDbToUsuarioList);
  }

  // Filtrado opcional
  @Override
  public List<UsuarioList> findByUsernameContaining(String username) {
    return usuarioRepository.findAll().stream()
      .filter(u -> u.getUsername().toLowerCase().contains(username.toLowerCase()))
      .map(mapper::UsuarioDbToUsuarioList)
      .collect(Collectors.toList());
  }

  // Update
  @Override
  public UsuarioView updateUsuario(Long id, UsuarioEdit usuarioEdit) {
    UsuarioDb usuarioDb = usuarioRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    Set<RolDb> roles = resolveRoles(usuarioEdit.getRolIds());

    mapper.updateUsuarioDbFromUsuarioEdit(usuarioEdit, usuarioDb);
    usuarioDb.setRoles(roles);
    usuarioDb = usuarioRepository.save(usuarioDb);
    return mapper.UsuarioDbToUsuarioView(usuarioDb);
  }

  // Delete
  @Override
  public void deleteUsuario(Long id) {
    if (!usuarioRepository.existsById(id)) {
      throw new RuntimeException("Usuario no encontrado");
    }
    usuarioRepository.deleteById(id);
  }

  private Set<RolDb> resolveRoles(Set<Long> rolIds) {
    Set<RolDb> roles = rolRepository.findAllById(rolIds).stream().collect(Collectors.toSet());
    if (roles.size() != rolIds.size()) {
      throw new RuntimeException("Uno o más roles no existen");
    }
    return roles;
  }
}
