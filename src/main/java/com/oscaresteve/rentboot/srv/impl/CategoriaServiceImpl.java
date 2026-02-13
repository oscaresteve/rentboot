package com.oscaresteve.rentboot.srv.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oscaresteve.rentboot.model.db.CategoriaDb;
import com.oscaresteve.rentboot.model.dto.categoria.CategoriaEdit;
import com.oscaresteve.rentboot.model.dto.categoria.CategoriaList;
import com.oscaresteve.rentboot.model.dto.categoria.CategoriaView;
import com.oscaresteve.rentboot.repository.CategoriaRepository;
import com.oscaresteve.rentboot.srv.CategoriaService;
import com.oscaresteve.rentboot.srv.mapper.CategoriaMapper;

@Service
public class CategoriaServiceImpl implements CategoriaService {

  @Autowired
  private CategoriaRepository categoriaRepository;

  private final CategoriaMapper mapper = CategoriaMapper.INSTANCE;

  // Create
  @Override
  public CategoriaView createCategoria(CategoriaEdit categoriaEdit) {
    CategoriaDb categoriaDb = mapper.CategoriaEditToCategoriaDb(categoriaEdit);
    categoriaDb = categoriaRepository.save(categoriaDb);
    return mapper.CategoriaDbToCategoriaView(categoriaDb);
  }

  // Read
  @Override
  public CategoriaView getCategoriaById(Long id) {
    CategoriaDb categoriaDb = categoriaRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    return mapper.CategoriaDbToCategoriaView(categoriaDb);
  }

  @Override
  public Page<CategoriaList> getAllCategorias(Pageable pageable) {
    Page<CategoriaDb> page = categoriaRepository.findAll(pageable);
    return page.map(mapper::CategoriaDbToCategoriaList);
  }

  // Filtrado opcional
  @Override
  public List<CategoriaList> findByNombreContaining(String nombre) {
    return categoriaRepository.findAll().stream()
      .filter(c -> c.getNombre().toLowerCase().contains(nombre.toLowerCase()))
      .map(mapper::CategoriaDbToCategoriaList)
      .collect(Collectors.toList());
  }

  // Update
  @Override
  public CategoriaView updateCategoria(Long id, CategoriaEdit categoriaEdit) {
    CategoriaDb categoriaDb = categoriaRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    mapper.updateCategoriaDbFromCategoriaEdit(categoriaEdit, categoriaDb);
    categoriaDb = categoriaRepository.save(categoriaDb);
    return mapper.CategoriaDbToCategoriaView(categoriaDb);
  }

  // Delete
  @Override
  public void deleteCategoria(Long id) {
    if (!categoriaRepository.existsById(id)) {
      throw new RuntimeException("Categoria no encontrada");
    }
    categoriaRepository.deleteById(id);
  }
}
