package com.oscaresteve.rentboot.controller;

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

import com.oscaresteve.rentboot.exception.CustomErrorResponse;
import com.oscaresteve.rentboot.helper.PaginationHelper;
import com.oscaresteve.rentboot.model.dto.cliente.ClienteEdit;
import com.oscaresteve.rentboot.model.dto.cliente.ClienteList;
import com.oscaresteve.rentboot.model.dto.cliente.ClienteView;
import com.oscaresteve.rentboot.srv.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operaciones CRUD y de filtrado para clientes")
public class ClienteController {

  private final ClienteService clienteService;

  public ClienteController(ClienteService clienteService) {
    this.clienteService = clienteService;
  }

  // Create
  @PostMapping
  @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Cliente creado correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteView.class)) }),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "409", description = "Conflicto de integridad de datos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<ClienteView> createCliente(@Valid @RequestBody ClienteEdit clienteEdit) {
    ClienteView created = clienteService.createCliente(clienteEdit);
    return ResponseEntity.ok(created);
  }

  // Read
  @GetMapping("/{id}")
  @Operation(summary = "Obtener cliente por ID", description = "Retorna el detalle de un cliente por su identificador")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Cliente obtenido correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteView.class)) }),
      @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<ClienteView> getClienteById(
      @Parameter(description = "ID del cliente", example = "1", required = true)
      @PathVariable Long id
  ) {
    ClienteView clienteView = clienteService.getClienteById(id);
    return ResponseEntity.ok(clienteView);
  }

  @GetMapping
  @Operation(summary = "Listar clientes", description = "Retorna una lista paginada de clientes con ordenacion")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametros de paginacion u ordenacion invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Page<ClienteList>> getAllClientes(
      @Parameter(description = "Numero de pagina (base 0)", example = "0")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Tamano de pagina", example = "10")
      @RequestParam(defaultValue = "10") int size,
      @Parameter(description = "Ordenacion en formato campo,direccion (asc|desc). Repetible", example = "id,asc")
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(clienteService.getAllClientes(pageable));
  }

  // Filtrado
  @GetMapping("/nombre/{nombre}")
  @Operation(summary = "Filtrar clientes por nombre", description = "Retorna clientes paginados filtrados por nombre")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametros de filtro o paginacion invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Page<ClienteList>> getClientesByNombre(
      @Parameter(description = "Nombre o fragmento de nombre para filtrar clientes", example = "Ana", required = true)
      @PathVariable String nombre,
      @Parameter(description = "Numero de pagina (base 0)", example = "0")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Tamano de pagina", example = "10")
      @RequestParam(defaultValue = "10") int size,
      @Parameter(description = "Ordenacion en formato campo,direccion (asc|desc). Repetible", example = "id,asc")
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(clienteService.getClientesByNombre(nombre, pageable));
  }

  @GetMapping("/email/{email}")
  @Operation(summary = "Filtrar clientes por email", description = "Retorna clientes paginados filtrados por email")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametros de filtro o paginacion invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Page<ClienteList>> getClientesByEmail(
      @Parameter(description = "Email del cliente para filtrar", example = "ana@mail.com", required = true)
      @PathVariable String email,
      @Parameter(description = "Numero de pagina (base 0)", example = "0")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Tamano de pagina", example = "10")
      @RequestParam(defaultValue = "10") int size,
      @Parameter(description = "Ordenacion en formato campo,direccion (asc|desc). Repetible", example = "id,asc")
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(clienteService.getClientesByEmail(email, pageable));
  }

  @GetMapping("/telefono/{telefono}")
  @Operation(summary = "Filtrar clientes por telefono", description = "Retorna clientes paginados filtrados por telefono")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametros de filtro o paginacion invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Page<ClienteList>> getClientesByTelefono(
      @Parameter(description = "Telefono del cliente para filtrar", example = "600123123", required = true)
      @PathVariable String telefono,
      @Parameter(description = "Numero de pagina (base 0)", example = "0")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Tamano de pagina", example = "10")
      @RequestParam(defaultValue = "10") int size,
      @Parameter(description = "Ordenacion en formato campo,direccion (asc|desc). Repetible", example = "id,asc")
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(clienteService.getClientesByTelefono(telefono, pageable));
  }

  // Update
  @PutMapping("/{id}")
  @Operation(summary = "Actualizar cliente", description = "Actualiza un cliente existente por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteView.class)) }),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "409", description = "Conflicto de integridad de datos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<ClienteView> updateCliente(
      @Parameter(description = "ID del cliente", example = "1", required = true)
      @PathVariable Long id,
      @Valid @RequestBody ClienteEdit clienteEdit
  ) {
    ClienteView updated = clienteService.updateCliente(id, clienteEdit);
    return ResponseEntity.ok(updated);
  }

  // Delete
  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar cliente", description = "Elimina un cliente existente por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
      @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Void> deleteCliente(
      @Parameter(description = "ID del cliente", example = "1", required = true)
      @PathVariable Long id
  ) {
    clienteService.deleteCliente(id);
    return ResponseEntity.noContent().build();
  }
}
