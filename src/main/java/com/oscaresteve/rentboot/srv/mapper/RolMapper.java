package com.oscaresteve.rentboot.srv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.oscaresteve.rentboot.model.db.RolDb;
import com.oscaresteve.rentboot.model.dto.rol.RolEdit;
import com.oscaresteve.rentboot.model.dto.rol.RolList;
import com.oscaresteve.rentboot.model.dto.rol.RolView;

@Mapper
public interface RolMapper {
  // Edit to Db
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "usuarios", ignore = true)
  RolDb RolEditToRolDb(RolEdit rolEdit);

  // Db to Edit
  RolEdit RolDbToRolEdit(RolDb rolDb);

  // Db to List
  RolList RolDbToRolList(RolDb rolDb);

  // Db to View
  RolView RolDbToRolView(RolDb rolDb);

  // Actualizar DB desde DTO
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "usuarios", ignore = true)
  void updateRolDbFromRolEdit(RolEdit rolEdit, @MappingTarget RolDb rolDb);
}



