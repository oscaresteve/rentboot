package com.oscaresteve.rentboot.srv;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oscaresteve.rentboot.model.dto.cliente.ClienteEdit;
import com.oscaresteve.rentboot.model.dto.cliente.ClienteList;
import com.oscaresteve.rentboot.model.dto.cliente.ClienteView;

public interface ClienteService {

  // Create
  ClienteView createCliente(ClienteEdit clienteEdit);

  // Read
  ClienteView getClienteById(Long id);

  Page<ClienteList> getAllClientes(Pageable pageable);

  // Filtrado
  Page<ClienteList> getClientesByNombre(String nombre, Pageable pageable);

  Page<ClienteList> getClientesByEmail(String email, Pageable pageable);

  Page<ClienteList> getClientesByTelefono(String telefono, Pageable pageable);

  // Update
  ClienteView updateCliente(Long id, ClienteEdit clienteEdit);

  // Delete
  void deleteCliente(Long id);
}
