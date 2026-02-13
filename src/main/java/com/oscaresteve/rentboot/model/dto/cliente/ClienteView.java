package com.oscaresteve.rentboot.model.dto.cliente;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteView {

    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private LocalDateTime fechaRegistro;
}