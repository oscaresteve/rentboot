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
import com.oscaresteve.rentboot.model.dto.rol.RolEdit;
import com.oscaresteve.rentboot.model.dto.rol.RolList;
import com.oscaresteve.rentboot.model.dto.rol.RolView;
import com.oscaresteve.rentboot.srv.RolService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roles")
public class RolController {

  @Autowired
  private RolService rolService;

  // Create
  @PostMapping
  public ResponseEntity<RolView> createRol(@Valid @RequestBody RolEdit rolEdit) {
    RolView created = rolService.createRol(rolEdit);
    return ResponseEntity.ok(created);
  }

  // Read
  @GetMapping("/{id}")
  public ResponseEntity<RolView> getRolById(@PathVariable Long id) {
    RolView rolView = rolService.getRolById(id);
    return ResponseEntity.ok(rolView);
  }

  @GetMapping
  public ResponseEntity<Page<RolList>> getAllRoles(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(rolService.getAllRoles(pageable));
  }

  // Filtrado
  @GetMapping("/nombre/{nombre}")
  public ResponseEntity<Page<RolList>> getRolesByNombre(
      @PathVariable String nombre,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(rolService.getRolesByNombre(nombre, pageable));
  }

  // Update
  @PutMapping("/{id}")
  public ResponseEntity<RolView> updateRol(
      @PathVariable Long id,
      @Valid @RequestBody RolEdit rolEdit
  ) {
    RolView updated = rolService.updateRol(id, rolEdit);
    return ResponseEntity.ok(updated);
  }

  // Delete
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
    rolService.deleteRol(id);
    return ResponseEntity.noContent().build();
  }
}
