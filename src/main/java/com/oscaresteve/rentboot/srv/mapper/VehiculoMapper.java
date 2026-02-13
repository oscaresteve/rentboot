package com.oscaresteve.rentboot.srv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.oscaresteve.rentboot.model.db.CategoriaDb;
import com.oscaresteve.rentboot.model.db.VehiculoDb;
import com.oscaresteve.rentboot.model.dto.vehiculo.VehiculoEdit;
import com.oscaresteve.rentboot.model.dto.vehiculo.VehiculoList;
import com.oscaresteve.rentboot.model.dto.vehiculo.VehiculoView;

@Mapper
public interface VehiculoMapper {

  VehiculoMapper INSTANCE = Mappers.getMapper(VehiculoMapper.class);

  // Edit to Db
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "alquileres", ignore = true)
  @Mapping(target = "categoria", source = "categoriaId", qualifiedByName = "categoriaFromId")
  VehiculoDb VehiculoEditToVehiculoDb(VehiculoEdit vehiculoEdit);

  // Db to Edit
  @Mapping(target = "categoriaId", source = "categoria.id")
  VehiculoEdit VehiculoDbToVehiculoEdit(VehiculoDb vehiculoDb);

  // Db to List
  @Mapping(target = "categoriaId", source = "categoria.id")
  VehiculoList VehiculoDbToVehiculoList(VehiculoDb vehiculoDb);

  // Db to View
  @Mapping(target = "categoriaId", source = "categoria.id")
  VehiculoView VehiculoDbToVehiculoView(VehiculoDb vehiculoDb);

  // Actualizar DB desde DTO
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "alquileres", ignore = true)
  @Mapping(target = "categoria", source = "categoriaId", qualifiedByName = "categoriaFromId")
  void updateVehiculoDbFromVehiculoEdit(VehiculoEdit vehiculoEdit, @MappingTarget VehiculoDb vehiculoDb);

  @Named("categoriaFromId")
  default CategoriaDb categoriaFromId(Long id) {
    if (id == null) {
      return null;
    }
    CategoriaDb categoria = new CategoriaDb();
    categoria.setId(id);
    return categoria;
  }
}
