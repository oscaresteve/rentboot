package com.oscaresteve.rentboot.srv.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oscaresteve.rentboot.exception.EntityNotFoundException;
import com.oscaresteve.rentboot.model.db.ClienteDb;
import com.oscaresteve.rentboot.model.dto.cliente.ClienteEdit;
import com.oscaresteve.rentboot.model.dto.cliente.ClienteList;
import com.oscaresteve.rentboot.model.dto.cliente.ClienteView;
import com.oscaresteve.rentboot.repository.ClienteRepository;
import com.oscaresteve.rentboot.srv.ClienteService;
import com.oscaresteve.rentboot.srv.mapper.ClienteMapper;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private final ClienteMapper mapper = ClienteMapper.INSTANCE;

    // Create
    @Override
    public ClienteView createCliente(ClienteEdit clienteEdit) {
        ClienteDb clienteDb = mapper.ClienteEditToClienteDb(clienteEdit);
        clienteDb = clienteRepository.save(clienteDb);
        return mapper.ClienteDbToClienteView(clienteDb);
    }

    // Read
    @Override
    public ClienteView getClienteById(Long id) {
        ClienteDb clienteDb = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CLIENTE_NOT_FOUND", "Cliente no encontrado"));
        return mapper.ClienteDbToClienteView(clienteDb);
    }

    @Override
    public Page<ClienteList> getAllClientes(Pageable pageable) {
        Page<ClienteDb> page = clienteRepository.findAll(pageable);
        return page.map(mapper::ClienteDbToClienteList);
    }

    // Filtrado
    @Override
    public Page<ClienteList> getClientesByNombre(String nombre, Pageable pageable) {
        Page<ClienteDb> page = clienteRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        return page.map(mapper::ClienteDbToClienteList);
    }

    @Override
    public Page<ClienteList> getClientesByEmail(String email, Pageable pageable) {
        Page<ClienteDb> page = clienteRepository.findByEmailContainingIgnoreCase(email, pageable);
        return page.map(mapper::ClienteDbToClienteList);
    }

    @Override
    public Page<ClienteList> getClientesByTelefono(String telefono, Pageable pageable) {
        Page<ClienteDb> page = clienteRepository.findByTelefonoContainingIgnoreCase(telefono, pageable);
        return page.map(mapper::ClienteDbToClienteList);
    }

    // Update
    @Override
    public ClienteView updateCliente(Long id, ClienteEdit clienteEdit) {
        ClienteDb clienteDb = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CLIENTE_NOT_FOUND", "Cliente no encontrado"));
        mapper.updateClienteDbFromClienteEdit(clienteEdit, clienteDb);
        clienteDb = clienteRepository.save(clienteDb);
        return mapper.ClienteDbToClienteView(clienteDb);
    }

    // Delete
    @Override
    public void deleteCliente(Long id) {
        if(!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("CLIENTE_NOT_FOUND", "Cliente no encontrado");
        }
        clienteRepository.deleteById(id);
    }
}
