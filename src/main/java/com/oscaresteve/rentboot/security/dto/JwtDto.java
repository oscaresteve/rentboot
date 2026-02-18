package com.oscaresteve.rentboot.security.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtDto {

  private String token;
  private String type;
  private String username;
  private List<String> roles;
}
