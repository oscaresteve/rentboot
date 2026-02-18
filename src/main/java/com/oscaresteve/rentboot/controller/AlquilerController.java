package com.oscaresteve.rentboot.controller;

import java.util.List;

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
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerCategoriaStats;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerClienteStats;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerEdit;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerList;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerVehiculoStats;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerView;
import com.oscaresteve.rentboot.srv.AlquilerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alquileres")
@Tag(name = "Alquileres", description = "Operaciones CRUD y de filtrado para alquileres")
public class AlquilerController {

  private final AlquilerService alquilerService;

  public AlquilerController(AlquilerService alquilerService) {
    this.alquilerService = alquilerService;
  }

  // Create
  @PostMapping
  @Operation(summary = "Crear alquiler", description = "Crea un nuevo alquiler")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Alquiler creado correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = AlquilerView.class)) }),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "409", description = "Conflicto de integridad de datos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<AlquilerView> createAlquiler(@Valid @RequestBody AlquilerEdit alquilerEdit) {
    AlquilerView created = alquilerService.createAlquiler(alquilerEdit);
    return ResponseEntity.ok(created);
  }

  // Read
  @GetMapping("/{id}")
  @Operation(summary = "Obtener alquiler por ID", description = "Retorna el detalle de un alquiler por su identificador")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Alquiler obtenido correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = AlquilerView.class)) }),
      @ApiResponse(responseCode = "404", description = "Alquiler no encontrado", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<AlquilerView> getAlquilerById(
      @Parameter(description = "ID del alquiler", example = "1", required = true)
      @PathVariable Long id
  ) {
    AlquilerView alquilerView = alquilerService.getAlquilerById(id);
    return ResponseEntity.ok(alquilerView);
  }

  @GetMapping
  @Operation(summary = "Listar alquileres", description = "Retorna una lista paginada de alquileres con ordenacion")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de alquileres obtenida correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametros de paginacion u ordenacion invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Page<AlquilerList>> getAllAlquileres(
      @Parameter(description = "Numero de pagina (base 0)", example = "0")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Tamano de pagina", example = "10")
      @RequestParam(defaultValue = "10") int size,
      @Parameter(description = "Ordenacion en formato campo,direccion (asc|desc). Repetible", example = "id,asc")
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(alquilerService.getAllAlquileres(pageable));
  }

  // Filtrado
  @GetMapping("/cliente/{clienteId}")
  @Operation(summary = "Filtrar alquileres por cliente", description = "Retorna alquileres paginados filtrados por identificador de cliente")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de alquileres obtenida correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametros de filtro o paginacion invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Page<AlquilerList>> getAlquileresByClienteId(
      @Parameter(description = "ID del cliente", example = "1", required = true)
      @PathVariable Long clienteId,
      @Parameter(description = "Numero de pagina (base 0)", example = "0")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Tamano de pagina", example = "10")
      @RequestParam(defaultValue = "10") int size,
      @Parameter(description = "Ordenacion en formato campo,direccion (asc|desc). Repetible", example = "id,asc")
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(alquilerService.getAlquileresByClienteId(clienteId, pageable));
  }

  @GetMapping("/vehiculo/{vehiculoId}")
  @Operation(summary = "Filtrar alquileres por vehiculo", description = "Retorna alquileres paginados filtrados por identificador de vehiculo")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de alquileres obtenida correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametros de filtro o paginacion invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Page<AlquilerList>> getAlquileresByVehiculoId(
      @Parameter(description = "ID del vehiculo", example = "1", required = true)
      @PathVariable Long vehiculoId,
      @Parameter(description = "Numero de pagina (base 0)", example = "0")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Tamano de pagina", example = "10")
      @RequestParam(defaultValue = "10") int size,
      @Parameter(description = "Ordenacion en formato campo,direccion (asc|desc). Repetible", example = "id,asc")
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(alquilerService.getAlquileresByVehiculoId(vehiculoId, pageable));
  }

  // Agregaciones
  @GetMapping("/stats/categoria")
  @Operation(summary = "Estadisticas por categoria", description = "Retorna conteo, suma y promedio de alquileres agrupados por categoria")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Estadisticas por categoria obtenidas correctamente"),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<List<AlquilerCategoriaStats>> getStatsByCategoria() {
    return ResponseEntity.ok(alquilerService.getStatsByCategoria());
  }

  @GetMapping("/stats/vehiculo/top")
  @Operation(summary = "Top vehiculos por alquileres", description = "Retorna los vehiculos con mayor cantidad de alquileres")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Top de vehiculos obtenido correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametro limit invalido", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<List<AlquilerVehiculoStats>> getTopVehiculos(
      @Parameter(description = "Cantidad maxima de vehiculos a retornar", example = "5")
      @RequestParam(defaultValue = "5") int limit
  ) {
    return ResponseEntity.ok(alquilerService.getTopVehiculos(limit));
  }

  @GetMapping("/stats/clientes/top")
  @Operation(summary = "Top clientes por alquileres", description = "Retorna los clientes con mayor cantidad de alquileres y su gasto")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Top de clientes obtenido correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametro limit invalido", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<List<AlquilerClienteStats>> getTopClientes(
      @Parameter(description = "Cantidad maxima de clientes a retornar", example = "10")
      @RequestParam(defaultValue = "10") int limit
  ) {
    return ResponseEntity.ok(alquilerService.getTopClientes(limit));
  }

  // Update
  @PutMapping("/{id}")
  @Operation(summary = "Actualizar alquiler", description = "Actualiza un alquiler existente por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Alquiler actualizado correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = AlquilerView.class)) }),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "404", description = "Alquiler no encontrado", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "409", description = "Conflicto de integridad de datos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<AlquilerView> updateAlquiler(
      @Parameter(description = "ID del alquiler", example = "1", required = true)
      @PathVariable Long id,
      @Valid @RequestBody AlquilerEdit alquilerEdit
  ) {
    AlquilerView updated = alquilerService.updateAlquiler(id, alquilerEdit);
    return ResponseEntity.ok(updated);
  }

  // Delete
  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar alquiler", description = "Elimina un alquiler existente por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Alquiler eliminado correctamente"),
      @ApiResponse(responseCode = "404", description = "Alquiler no encontrado", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Void> deleteAlquiler(
      @Parameter(description = "ID del alquiler", example = "1", required = true)
      @PathVariable Long id
  ) {
    alquilerService.deleteAlquiler(id);
    return ResponseEntity.noContent().build();
  }
}
