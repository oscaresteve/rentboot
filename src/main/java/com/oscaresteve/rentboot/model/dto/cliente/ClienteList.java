package com.oscaresteve.rentboot.model.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteList {

    private Long id;
    private String nombre;
    private String email;
    private String telefono;
}