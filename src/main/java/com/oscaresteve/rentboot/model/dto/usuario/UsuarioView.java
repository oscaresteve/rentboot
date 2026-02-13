package com.oscaresteve.rentboot.model.dto.usuario;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioView {

  private Long id;
  private String username;
  private Boolean enabled;
  private Set<Long> rolIds;
}
