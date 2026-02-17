package com.oscaresteve.rentboot.srv;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oscaresteve.rentboot.model.dto.usuario.UsuarioEdit;
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioList;
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioView;

public interface UsuarioService {

  // Create
  UsuarioView createUsuario(UsuarioEdit usuarioEdit);

  // Read
  UsuarioView getUsuarioById(Long id);

  Page<UsuarioList> getAllUsuarios(Pageable pageable);

  // Filtrado
  Page<UsuarioList> getUsuariosByUsername(String username, Pageable pageable);

  // Update
  UsuarioView updateUsuario(Long id, UsuarioEdit usuarioEdit);

  // Delete
  void deleteUsuario(Long id);
}
