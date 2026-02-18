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
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerEdit;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerList;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerView;
import com.oscaresteve.rentboot.srv.AlquilerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

  @Autowired
  private AlquilerService alquilerService;

  // Create
  @PostMapping
  public ResponseEntity<AlquilerView> createAlquiler(@Valid @RequestBody AlquilerEdit alquilerEdit) {
    AlquilerView created = alquilerService.createAlquiler(alquilerEdit);
    return ResponseEntity.ok(created);
  }

  // Read
  @GetMapping("/{id}")
  public ResponseEntity<AlquilerView> getAlquilerById(@PathVariable Long id) {
    AlquilerView alquilerView = alquilerService.getAlquilerById(id);
    return ResponseEntity.ok(alquilerView);
  }

  @GetMapping
  public ResponseEntity<Page<AlquilerList>> getAllAlquileres(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(alquilerService.getAllAlquileres(pageable));
  }

  // Filtrado
  @GetMapping("/cliente/{clienteId}")
  public ResponseEntity<Page<AlquilerList>> getAlquileresByClienteId(
      @PathVariable Long clienteId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(alquilerService.getAlquileresByClienteId(clienteId, pageable));
  }

  @GetMapping("/vehiculo/{vehiculoId}")
  public ResponseEntity<Page<AlquilerList>> getAlquileresByVehiculoId(
      @PathVariable Long vehiculoId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(alquilerService.getAlquileresByVehiculoId(vehiculoId, pageable));
  }

  // Update
  @PutMapping("/{id}")
  public ResponseEntity<AlquilerView> updateAlquiler(
      @PathVariable Long id,
      @Valid @RequestBody AlquilerEdit alquilerEdit
  ) {
    AlquilerView updated = alquilerService.updateAlquiler(id, alquilerEdit);
    return ResponseEntity.ok(updated);
  }

  // Delete
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAlquiler(@PathVariable Long id) {
    alquilerService.deleteAlquiler(id);
    return ResponseEntity.noContent().build();
  }
}
