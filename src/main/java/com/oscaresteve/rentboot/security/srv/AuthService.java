package com.oscaresteve.rentboot.security.srv;

import com.oscaresteve.rentboot.model.dto.usuario.UsuarioView;
import com.oscaresteve.rentboot.security.dto.JwtDto;
import com.oscaresteve.rentboot.security.dto.LoginRequest;
import com.oscaresteve.rentboot.security.dto.RegisterRequest;

public interface AuthService {

  JwtDto login(LoginRequest request);

  UsuarioView register(RegisterRequest request);
}
