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
import com.oscaresteve.rentboot.exception.CustomErrorResponse;
import com.oscaresteve.rentboot.model.dto.categoria.CategoriaEdit;
import com.oscaresteve.rentboot.model.dto.categoria.CategoriaList;
import com.oscaresteve.rentboot.model.dto.categoria.CategoriaView;
import com.oscaresteve.rentboot.srv.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorias", description = "Operaciones CRUD y de filtrado para categorias")
public class CategoriaController {

  @Autowired
  private CategoriaService categoriaService;

  // Create
  @PostMapping
  @Operation(summary = "Crear categoria", description = "Crea una nueva categoria")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Categoria creada correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaView.class)) }),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "409", description = "Conflicto de integridad de datos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<CategoriaView> createCategoria(@Valid @RequestBody CategoriaEdit categoriaEdit) {
    CategoriaView created = categoriaService.createCategoria(categoriaEdit);
    return ResponseEntity.ok(created);
  }

  // Read
  @GetMapping("/{id}")
  @Operation(summary = "Obtener categoria por ID", description = "Retorna el detalle de una categoria por su identificador")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Categoria obtenida correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaView.class)) }),
      @ApiResponse(responseCode = "404", description = "Categoria no encontrada", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<CategoriaView> getCategoriaById(@PathVariable Long id) {
    CategoriaView categoriaView = categoriaService.getCategoriaById(id);
    return ResponseEntity.ok(categoriaView);
  }

  @GetMapping
  @Operation(summary = "Listar categorias", description = "Retorna una lista paginada de categorias con ordenacion")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de categorias obtenida correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametros de paginacion u ordenacion invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Page<CategoriaList>> getAllCategorias(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(categoriaService.getAllCategorias(pageable));
  }

  // Filtrado
  @GetMapping("/nombre/{nombre}")
  @Operation(summary = "Filtrar categorias por nombre", description = "Retorna categorias paginadas filtradas por nombre")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Lista de categorias obtenida correctamente"),
      @ApiResponse(responseCode = "400", description = "Parametros de filtro o paginacion invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Page<CategoriaList>> getCategoriasByNombre(
      @PathVariable String nombre,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort
  ) {
    Pageable pageable = PaginationHelper.createPageable(page, size, sort);
    return ResponseEntity.ok(categoriaService.getCategoriasByNombre(nombre, pageable));
  }

  // Update
  @PutMapping("/{id}")
  @Operation(summary = "Actualizar categoria", description = "Actualiza una categoria existente por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Categoria actualizada correctamente", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaView.class)) }),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "404", description = "Categoria no encontrada", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "409", description = "Conflicto de integridad de datos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<CategoriaView> updateCategoria(
      @PathVariable Long id,
      @Valid @RequestBody CategoriaEdit categoriaEdit
  ) {
    CategoriaView updated = categoriaService.updateCategoria(id, categoriaEdit);
    return ResponseEntity.ok(updated);
  }

  // Delete
  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar categoria", description = "Elimina una categoria existente por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Categoria eliminada correctamente"),
      @ApiResponse(responseCode = "404", description = "Categoria no encontrada", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
    categoriaService.deleteCategoria(id);
    return ResponseEntity.noContent().build();
  }
}
