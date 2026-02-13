package com.oscaresteve.rentboot.srv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.oscaresteve.rentboot.model.db.CategoriaDb;
import com.oscaresteve.rentboot.model.dto.categoria.CategoriaEdit;
import com.oscaresteve.rentboot.model.dto.categoria.CategoriaList;
import com.oscaresteve.rentboot.model.dto.categoria.CategoriaView;

@Mapper
public interface CategoriaMapper {

  CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

  // Edit to Db
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "vehiculos", ignore = true)
  CategoriaDb CategoriaEditToCategoriaDb(CategoriaEdit categoriaEdit);

  // Db to Edit
  CategoriaEdit CategoriaDbToCategoriaEdit(CategoriaDb categoriaDb);

  // Db to List
  CategoriaList CategoriaDbToCategoriaList(CategoriaDb categoriaDb);

  // Db to View
  CategoriaView CategoriaDbToCategoriaView(CategoriaDb categoriaDb);

  // Actualizar DB desde DTO
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "vehiculos", ignore = true)
  void updateCategoriaDbFromCategoriaEdit(CategoriaEdit categoriaEdit, @MappingTarget CategoriaDb categoriaDb);
}
