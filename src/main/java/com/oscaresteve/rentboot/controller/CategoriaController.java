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
import com.oscaresteve.rentboot.model.dto.categoria.CategoriaEdit;
import com.oscaresteve.rentboot.model.dto.categoria.CategoriaList;
import com.oscaresteve.rentboot.model.dto.categoria.CategoriaView;
import com.oscaresteve.rentboot.srv.CategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

  @Autowired
  private CategoriaService categoriaService;

  // Create
  @PostMapping
  public ResponseEntity<CategoriaView> createCategoria(@Valid @RequestBody CategoriaEdit categoriaEdit) {
    CategoriaView created = categoriaService.createCategoria(categoriaEdit);
    return ResponseEntity.ok(created);
  }

  // Read
  @GetMapping("/{id}")
  public ResponseEntity<CategoriaView> getCategoriaById(@PathVariable Long id) {
    CategoriaView categoriaView = categoriaService.getCategoriaById(id);
    return ResponseEntity.ok(categoriaView);
  }

  @GetMapping
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
  public ResponseEntity<CategoriaView> updateCategoria(
      @PathVariable Long id,
      @Valid @RequestBody CategoriaEdit categoriaEdit
  ) {
    CategoriaView updated = categoriaService.updateCategoria(id, categoriaEdit);
    return ResponseEntity.ok(updated);
  }

  // Delete
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
    categoriaService.deleteCategoria(id);
    return ResponseEntity.noContent().build();
  }
}
