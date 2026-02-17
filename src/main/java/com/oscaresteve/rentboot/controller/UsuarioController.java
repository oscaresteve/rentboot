package com.oscaresteve.rentboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oscaresteve.rentboot.helper.PaginationHelper;
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioEdit;
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioList;
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioView;
import com.oscaresteve.rentboot.srv.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @PostMapping
  public ResponseEntity<UsuarioView> createUsuario(@Valid @RequestBody UsuarioEdit usuarioEdit) {
    UsuarioView created = usuarioService.createUsuario(usuarioEdit);
    return ResponseEntity.ok(created);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UsuarioView> getUsuarioById(@PathVariable Long id) {
    UsuarioView usuarioView = usuarioService.getUsuarioById(id);
    return ResponseEntity.ok(usuarioView);
  }

  @GetMapping
  public ResponseEntity<Page<UsuarioList>> getAllUsuarios(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(usuarioService.getAllUsuarios(pageable));
  }

  @GetMapping("/username/{username}")
  public ResponseEntity<Page<UsuarioList>> getUsuariosByUsername(
      @PathVariable String username,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(usuarioService.getUsuariosByUsername(username, pageable));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UsuarioView> updateUsuario(
      @PathVariable Long id,
      @Valid @RequestBody UsuarioEdit usuarioEdit
  ) {
    UsuarioView updated = usuarioService.updateUsuario(id, usuarioEdit);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
    usuarioService.deleteUsuario(id);
    return ResponseEntity.noContent().build();
  }
}
