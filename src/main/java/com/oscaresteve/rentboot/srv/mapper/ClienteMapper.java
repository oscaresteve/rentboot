package com.oscaresteve.rentboot.srv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.oscaresteve.rentboot.model.db.ClienteDb;
import com.oscaresteve.rentboot.model.dto.cliente.ClienteEdit;
import com.oscaresteve.rentboot.model.dto.cliente.ClienteList;
import com.oscaresteve.rentboot.model.dto.cliente.ClienteView;


@Mapper
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    //Edit to Db
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "alquileres", ignore = true)
    ClienteDb ClienteEditToClienteDb(ClienteEdit clienteEdit);

    //Db to Edit
    ClienteEdit ClienteDbToClienteEdit(ClienteDb clienteDb);
    
    //Db to List
    ClienteList ClienteDbToClienteList(ClienteDb clienteDb);

    //Db to View
    ClienteView ClienteDbToClienteView(ClienteDb clienteDb);

    // Actualizar DB desde DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "alquileres", ignore = true)
    void updateClienteDbFromClienteEdit(ClienteEdit clienteEdit, @MappingTarget ClienteDb clienteDb);
}
