package com.oscaresteve.rentboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
      @RequestParam(defaultValue = "id,asc") String[] sort,
      @RequestParam(required = false) String nombre
  ) {
    Sort sortOrder = Sort.by(Sort.Direction.fromString(sort[1]), sort[0]);
    Pageable pageable = PageRequest.of(page, size, sortOrder);

    Page<ClienteList> result;
    if (nombre != null && !nombre.isEmpty()) {
      List<ClienteList> filtered = clienteService.findByNombreContaining(nombre);
      result = new PageImpl<>(filtered, pageable, filtered.size());
    } else {
      result = clienteService.getAllClientes(pageable);
    }

    return ResponseEntity.ok(result);
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
