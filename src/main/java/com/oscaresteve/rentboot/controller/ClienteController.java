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
import com.oscaresteve.rentboot.model.dto.cliente.ClienteEdit;
import com.oscaresteve.rentboot.model.dto.cliente.ClienteList;
import com.oscaresteve.rentboot.model.dto.cliente.ClienteView;
import com.oscaresteve.rentboot.srv.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

  @Autowired
  private ClienteService clienteService;

  // Create
  @PostMapping
  public ResponseEntity<ClienteView> createCliente(@Valid @RequestBody ClienteEdit clienteEdit) {
    ClienteView created = clienteService.createCliente(clienteEdit);
    return ResponseEntity.ok(created);
  }

  // Read
  @GetMapping("/{id}")
  public ResponseEntity<ClienteView> getClienteById(@PathVariable Long id) {
    ClienteView clienteView = clienteService.getClienteById(id);
    return ResponseEntity.ok(clienteView);
  }

  @GetMapping
  public ResponseEntity<Page<ClienteList>> getAllClientes(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(clienteService.getAllClientes(pageable));
  }

  @GetMapping("/nombre/{nombre}")
  public ResponseEntity<Page<ClienteList>> getClientesByNombre(
      @PathVariable String nombre,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(clienteService.getClientesByNombre(nombre, pageable));
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<Page<ClienteList>> getClientesByEmail(
      @PathVariable String email,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(clienteService.getClientesByEmail(email, pageable));
  }

  @GetMapping("/telefono/{telefono}")
  public ResponseEntity<Page<ClienteList>> getClientesByTelefono(
      @PathVariable String telefono,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(clienteService.getClientesByTelefono(telefono, pageable));
  }

  // Update
  @PutMapping("/{id}")
  public ResponseEntity<ClienteView> updateCliente(
      @PathVariable Long id,
      @Valid @RequestBody ClienteEdit clienteEdit
  ) {
    ClienteView updated = clienteService.updateCliente(id, clienteEdit);
    return ResponseEntity.ok(updated);
  }

  // Delete
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
    clienteService.deleteCliente(id);
    return ResponseEntity.noContent().build();
  }
}
