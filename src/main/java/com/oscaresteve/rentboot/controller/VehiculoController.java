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
import com.oscaresteve.rentboot.model.dto.vehiculo.VehiculoEdit;
import com.oscaresteve.rentboot.model.dto.vehiculo.VehiculoList;
import com.oscaresteve.rentboot.model.dto.vehiculo.VehiculoView;
import com.oscaresteve.rentboot.srv.VehiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vehiculos")
@Tag(name = "Vehiculos", description = "Operaciones CRUD y de filtrado para vehiculos")
public class VehiculoController {

  private final VehiculoService vehiculoService;

  public VehiculoController(VehiculoService vehiculoService) {
    this.vehiculoService = vehiculoService;
  }

  // Create
  @PostMapping
  @Operation(summary = "Crear vehiculo", description = "Crea un nuevo vehiculo")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Vehiculo creado correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = VehiculoView.class)) }),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "409", description = "Conflicto de integridad de datos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<VehiculoView> createVehiculo(@Valid @RequestBody VehiculoEdit vehiculoEdit) {
    VehiculoView created = vehiculoService.createVehiculo(vehiculoEdit);
    return ResponseEntity.ok(created);
  }

  // Read
  @GetMapping("/{id}")
  @Operation(summary = "Obtener vehiculo por ID", description = "Retorna el detalle de un vehiculo por su identificador")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Vehiculo obtenido correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = VehiculoView.class)) }),
      @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<VehiculoView> getVehiculoById(
      @Parameter(description = "ID del vehiculo", example = "1", required = true)
      @PathVariable Long id
  ) {
    VehiculoView vehiculoView = vehiculoService.getVehiculoById(id);
    return ResponseEntity.ok(vehiculoView);
  }

  @GetMapping
  @Operation(summary = "Listar vehiculos", description = "Retorna una lista paginada de vehiculos con ordenacion")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de vehiculos obtenida correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametros de paginacion u ordenacion invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Page<VehiculoList>> getAllVehiculos(
      @Parameter(description = "Numero de pagina (base 0)", example = "0")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Tamano de pagina", example = "10")
      @RequestParam(defaultValue = "10") int size,
      @Parameter(description = "Ordenacion en formato campo,direccion (asc|desc). Repetible", example = "id,asc")
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(vehiculoService.getAllVehiculos(pageable));
  }

  // Filtrado
  @GetMapping("/disponible/{disponible}")
  @Operation(summary = "Filtrar vehiculos por disponibilidad", description = "Retorna vehiculos paginados filtrados por estado de disponibilidad")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de vehiculos obtenida correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametros de filtro o paginacion invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Page<VehiculoList>> getVehiculosByDisponible(
      @Parameter(description = "Estado de disponibilidad del vehiculo", example = "true", required = true)
      @PathVariable Boolean disponible,
      @Parameter(description = "Numero de pagina (base 0)", example = "0")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Tamano de pagina", example = "10")
      @RequestParam(defaultValue = "10") int size,
      @Parameter(description = "Ordenacion en formato campo,direccion (asc|desc). Repetible", example = "id,asc")
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(vehiculoService.getVehiculosByDisponible(disponible, pageable));
  }

  // Update
  @PutMapping("/{id}")
  @Operation(summary = "Actualizar vehiculo", description = "Actualiza un vehiculo existente por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Vehiculo actualizado correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = VehiculoView.class)) }),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "409", description = "Conflicto de integridad de datos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<VehiculoView> updateVehiculo(
      @Parameter(description = "ID del vehiculo", example = "1", required = true)
      @PathVariable Long id,
      @Valid @RequestBody VehiculoEdit vehiculoEdit
  ) {
    VehiculoView updated = vehiculoService.updateVehiculo(id, vehiculoEdit);
    return ResponseEntity.ok(updated);
  }

  // Delete
  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar vehiculo", description = "Elimina un vehiculo existente por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Vehiculo eliminado correctamente"),
      @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Void> deleteVehiculo(
      @Parameter(description = "ID del vehiculo", example = "1", required = true)
      @PathVariable Long id
  ) {
    vehiculoService.deleteVehiculo(id);
    return ResponseEntity.noContent().build();
  }
}
