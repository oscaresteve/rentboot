package com.oscaresteve.rentboot.srv;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.oscaresteve.rentboot.model.dto.categoria.CategoriaEdit;
import com.oscaresteve.rentboot.model.dto.categoria.CategoriaList;
import com.oscaresteve.rentboot.model.dto.categoria.CategoriaView;

public interface CategoriaService {

  // Create
  CategoriaView createCategoria(CategoriaEdit categoriaEdit);

  // Read
  CategoriaView getCategoriaById(Long id);

  Page<CategoriaList> getAllCategorias(Pageable pageable);

  // Filtrado
  Page<CategoriaList> getCategoriasByNombre(String nombre, Pageable pageable);

  // Update
  CategoriaView updateCategoria(Long id, CategoriaEdit categoriaEdit);

  // Delete
  void deleteCategoria(Long id);
}
