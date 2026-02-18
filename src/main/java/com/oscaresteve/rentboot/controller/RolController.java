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
import com.oscaresteve.rentboot.model.dto.rol.RolEdit;
import com.oscaresteve.rentboot.model.dto.rol.RolList;
import com.oscaresteve.rentboot.model.dto.rol.RolView;
import com.oscaresteve.rentboot.srv.RolService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "Operaciones CRUD y de filtrado para roles")
public class RolController {

  private final RolService rolService;

  public RolController(RolService rolService) {
    this.rolService = rolService;
  }

  // Create
  @PostMapping
  @Operation(summary = "Crear rol", description = "Crea un nuevo rol")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Rol creado correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = RolView.class)) }),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "409", description = "Conflicto de integridad de datos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<RolView> createRol(@Valid @RequestBody RolEdit rolEdit) {
    RolView created = rolService.createRol(rolEdit);
    return ResponseEntity.ok(created);
  }

  // Read
  @GetMapping("/{id}")
  @Operation(summary = "Obtener rol por ID", description = "Retorna el detalle de un rol por su identificador")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Rol obtenido correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = RolView.class)) }),
      @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<RolView> getRolById(
      @Parameter(description = "ID del rol", example = "1", required = true)
      @PathVariable Long id
  ) {
    RolView rolView = rolService.getRolById(id);
    return ResponseEntity.ok(rolView);
  }

  @GetMapping
  @Operation(summary = "Listar roles", description = "Retorna una lista paginada de roles con ordenacion")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de roles obtenida correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametros de paginacion u ordenacion invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Page<RolList>> getAllRoles(
      @Parameter(description = "Numero de pagina (base 0)", example = "0")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Tamano de pagina", example = "10")
      @RequestParam(defaultValue = "10") int size,
      @Parameter(description = "Ordenacion en formato campo,direccion (asc|desc). Repetible", example = "id,asc")
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(rolService.getAllRoles(pageable));
  }

  // Filtrado
  @GetMapping("/nombre/{nombre}")
  @Operation(summary = "Filtrar roles por nombre", description = "Retorna roles paginados filtrados por nombre")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de roles obtenida correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametros de filtro o paginacion invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Page<RolList>> getRolesByNombre(
      @Parameter(description = "Nombre o fragmento de nombre para filtrar roles", example = "ADMIN", required = true)
      @PathVariable String nombre,
      @Parameter(description = "Numero de pagina (base 0)", example = "0")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Tamano de pagina", example = "10")
      @RequestParam(defaultValue = "10") int size,
      @Parameter(description = "Ordenacion en formato campo,direccion (asc|desc). Repetible", example = "id,asc")
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(rolService.getRolesByNombre(nombre, pageable));
  }

  // Update
  @PutMapping("/{id}")
  @Operation(summary = "Actualizar rol", description = "Actualiza un rol existente por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = RolView.class)) }),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "409", description = "Conflicto de integridad de datos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<RolView> updateRol(
      @Parameter(description = "ID del rol", example = "1", required = true)
      @PathVariable Long id,
      @Valid @RequestBody RolEdit rolEdit
  ) {
    RolView updated = rolService.updateRol(id, rolEdit);
    return ResponseEntity.ok(updated);
  }

  // Delete
  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar rol", description = "Elimina un rol existente por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Rol eliminado correctamente"),
      @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Void> deleteRol(
      @Parameter(description = "ID del rol", example = "1", required = true)
      @PathVariable Long id
  ) {
    rolService.deleteRol(id);
    return ResponseEntity.noContent().build();
  }
}
