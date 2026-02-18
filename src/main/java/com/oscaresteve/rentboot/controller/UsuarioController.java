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
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioEdit;
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioList;
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioView;
import com.oscaresteve.rentboot.srv.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones CRUD y de filtrado para usuarios")
public class UsuarioController {

  private final UsuarioService usuarioService;

  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  // Create
  @PostMapping
  @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Usuario creado correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioView.class)) }),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "409", description = "Conflicto de integridad de datos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<UsuarioView> createUsuario(@Valid @RequestBody UsuarioEdit usuarioEdit) {
    UsuarioView created = usuarioService.createUsuario(usuarioEdit);
    return ResponseEntity.ok(created);
  }

  // Read
  @GetMapping("/{id}")
  @Operation(summary = "Obtener usuario por ID", description = "Retorna el detalle de un usuario por su identificador")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Usuario obtenido correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioView.class)) }),
      @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<UsuarioView> getUsuarioById(
      @Parameter(description = "ID del usuario", example = "1", required = true)
      @PathVariable Long id
  ) {
    UsuarioView usuarioView = usuarioService.getUsuarioById(id);
    return ResponseEntity.ok(usuarioView);
  }

  @GetMapping
  @Operation(summary = "Listar usuarios", description = "Retorna una lista paginada de usuarios con ordenacion")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametros de paginacion u ordenacion invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Page<UsuarioList>> getAllUsuarios(
      @Parameter(description = "Numero de pagina (base 0)", example = "0")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Tamano de pagina", example = "10")
      @RequestParam(defaultValue = "10") int size,
      @Parameter(description = "Ordenacion en formato campo,direccion (asc|desc). Repetible", example = "id,asc")
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(usuarioService.getAllUsuarios(pageable));
  }

  // Filtrado
  @GetMapping("/username/{username}")
  @Operation(summary = "Filtrar usuarios por username", description = "Retorna usuarios paginados filtrados por username")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametros de filtro o paginacion invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Page<UsuarioList>> getUsuariosByUsername(
      @Parameter(description = "Username para filtrar usuarios", example = "user.demo", required = true)
      @PathVariable String username,
      @Parameter(description = "Numero de pagina (base 0)", example = "0")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Tamano de pagina", example = "10")
      @RequestParam(defaultValue = "10") int size,
      @Parameter(description = "Ordenacion en formato campo,direccion (asc|desc). Repetible", example = "id,asc")
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(usuarioService.getUsuariosByUsername(username, pageable));
  }

  // Update
  @PutMapping("/{id}")
  @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario existente por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioView.class)) }),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "409", description = "Conflicto de integridad de datos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<UsuarioView> updateUsuario(
      @Parameter(description = "ID del usuario", example = "1", required = true)
      @PathVariable Long id,
      @Valid @RequestBody UsuarioEdit usuarioEdit
  ) {
    UsuarioView updated = usuarioService.updateUsuario(id, usuarioEdit);
    return ResponseEntity.ok(updated);
  }

  // Delete
  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar usuario", description = "Elimina un usuario existente por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
      @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Void> deleteUsuario(
      @Parameter(description = "ID del usuario", example = "1", required = true)
      @PathVariable Long id
  ) {
    usuarioService.deleteUsuario(id);
    return ResponseEntity.noContent().build();
  }
}
