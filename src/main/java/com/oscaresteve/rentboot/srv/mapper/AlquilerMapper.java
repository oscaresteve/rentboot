package com.oscaresteve.rentboot.srv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.oscaresteve.rentboot.model.db.AlquilerDb;
import com.oscaresteve.rentboot.model.db.ClienteDb;
import com.oscaresteve.rentboot.model.db.VehiculoDb;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerEdit;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerList;
import com.oscaresteve.rentboot.model.dto.alquiler.AlquilerView;

@Mapper
public interface AlquilerMapper {

  AlquilerMapper INSTANCE = Mappers.getMapper(AlquilerMapper.class);

  // Edit to Db
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "cliente", source = "clienteId", qualifiedByName = "clienteFromId")
  @Mapping(target = "vehiculo", source = "vehiculoId", qualifiedByName = "vehiculoFromId")
  AlquilerDb AlquilerEditToAlquilerDb(AlquilerEdit alquilerEdit);

  // Db to Edit
  @Mapping(target = "clienteId", source = "cliente.id")
  @Mapping(target = "vehiculoId", source = "vehiculo.id")
  AlquilerEdit AlquilerDbToAlquilerEdit(AlquilerDb alquilerDb);

  // Db to List
  @Mapping(target = "clienteId", source = "cliente.id")
  @Mapping(target = "vehiculoId", source = "vehiculo.id")
  AlquilerList AlquilerDbToAlquilerList(AlquilerDb alquilerDb);

  // Db to View
  @Mapping(target = "clienteId", source = "cliente.id")
  @Mapping(target = "vehiculoId", source = "vehiculo.id")
  AlquilerView AlquilerDbToAlquilerView(AlquilerDb alquilerDb);

  // Actualizar DB desde DTO
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "cliente", source = "clienteId", qualifiedByName = "clienteFromId")
  @Mapping(target = "vehiculo", source = "vehiculoId", qualifiedByName = "vehiculoFromId")
  void updateAlquilerDbFromAlquilerEdit(AlquilerEdit alquilerEdit, @MappingTarget AlquilerDb alquilerDb);

  @Named("clienteFromId")
  default ClienteDb clienteFromId(Long id) {
    if (id == null) {
      return null;
    }
    ClienteDb cliente = new ClienteDb();
    cliente.setId(id);
    return cliente;
  }

  @Named("vehiculoFromId")
  default VehiculoDb vehiculoFromId(Long id) {
    if (id == null) {
      return null;
    }
    VehiculoDb vehiculo = new VehiculoDb();
    vehiculo.setId(id);
    return vehiculo;
  }
}
