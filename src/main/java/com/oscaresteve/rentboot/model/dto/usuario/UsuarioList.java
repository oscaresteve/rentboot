package com.oscaresteve.rentboot.model.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioList {

  private Long id;
  private String username;
  private Boolean enabled;
}
