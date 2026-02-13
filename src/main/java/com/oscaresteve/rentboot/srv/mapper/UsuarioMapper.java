package com.oscaresteve.rentboot.srv.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.oscaresteve.rentboot.model.db.RolDb;
import com.oscaresteve.rentboot.model.db.UsuarioDb;
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioEdit;
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioList;
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioView;

@Mapper
public interface UsuarioMapper {

  UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

  // Edit to Db
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "roles", source = "rolIds", qualifiedByName = "rolesFromIds")
  UsuarioDb UsuarioEditToUsuarioDb(UsuarioEdit usuarioEdit);

  // Db to Edit
  @Mapping(target = "rolIds", source = "roles", qualifiedByName = "idsFromRoles")
  UsuarioEdit UsuarioDbToUsuarioEdit(UsuarioDb usuarioDb);

  // Db to List
  UsuarioList UsuarioDbToUsuarioList(UsuarioDb usuarioDb);

  // Db to View
  @Mapping(target = "rolIds", source = "roles", qualifiedByName = "idsFromRoles")
  UsuarioView UsuarioDbToUsuarioView(UsuarioDb usuarioDb);

  // Actualizar DB desde DTO
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "roles", source = "rolIds", qualifiedByName = "rolesFromIds")
  void updateUsuarioDbFromUsuarioEdit(UsuarioEdit usuarioEdit, @MappingTarget UsuarioDb usuarioDb);

  @Named("rolesFromIds")
  default Set<RolDb> rolesFromIds(Set<Long> ids) {
    if (ids == null) {
      return null;
    }
    return ids.stream().map(id -> {
      RolDb rol = new RolDb();
      rol.setId(id);
      return rol;
    }).collect(Collectors.toSet());
  }

  @Named("idsFromRoles")
  default Set<Long> idsFromRoles(Set<RolDb> roles) {
    if (roles == null) {
      return null;
    }
    return roles.stream()
      .map(RolDb::getId)
      .collect(Collectors.toSet());
  }
}
