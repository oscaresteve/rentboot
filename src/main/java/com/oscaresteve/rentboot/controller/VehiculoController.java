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
import com.oscaresteve.rentboot.model.dto.vehiculo.VehiculoEdit;
import com.oscaresteve.rentboot.model.dto.vehiculo.VehiculoList;
import com.oscaresteve.rentboot.model.dto.vehiculo.VehiculoView;
import com.oscaresteve.rentboot.srv.VehiculoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

  @Autowired
  private VehiculoService vehiculoService;

  @PostMapping
  public ResponseEntity<VehiculoView> createVehiculo(@Valid @RequestBody VehiculoEdit vehiculoEdit) {
    VehiculoView created = vehiculoService.createVehiculo(vehiculoEdit);
    return ResponseEntity.ok(created);
  }

  @GetMapping("/{id}")
  public ResponseEntity<VehiculoView> getVehiculoById(@PathVariable Long id) {
    VehiculoView vehiculoView = vehiculoService.getVehiculoById(id);
    return ResponseEntity.ok(vehiculoView);
  }

  @GetMapping
  public ResponseEntity<Page<VehiculoList>> getAllVehiculos(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(vehiculoService.getAllVehiculos(pageable));
  }

  @GetMapping("/disponible/{disponible}")
  public ResponseEntity<Page<VehiculoList>> getVehiculosByDisponible(
      @PathVariable Boolean disponible,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(vehiculoService.getVehiculosByDisponible(disponible, pageable));
  }

  @PutMapping("/{id}")
  public ResponseEntity<VehiculoView> updateVehiculo(
      @PathVariable Long id,
      @Valid @RequestBody VehiculoEdit vehiculoEdit
  ) {
    VehiculoView updated = vehiculoService.updateVehiculo(id, vehiculoEdit);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteVehiculo(@PathVariable Long id) {
    vehiculoService.deleteVehiculo(id);
    return ResponseEntity.noContent().build();
  }
}
