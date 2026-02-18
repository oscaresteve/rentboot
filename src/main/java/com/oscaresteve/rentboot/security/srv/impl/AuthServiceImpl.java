package com.oscaresteve.rentboot.security.srv.impl;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oscaresteve.rentboot.exception.DomainException;
import com.oscaresteve.rentboot.model.db.RolDb;
import com.oscaresteve.rentboot.model.db.UsuarioDb;
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioView;
import com.oscaresteve.rentboot.repository.RolRepository;
import com.oscaresteve.rentboot.repository.UsuarioRepository;
import com.oscaresteve.rentboot.security.dto.JwtDto;
import com.oscaresteve.rentboot.security.dto.LoginRequest;
import com.oscaresteve.rentboot.security.dto.RegisterRequest;
import com.oscaresteve.rentboot.security.srv.AuthService;
import com.oscaresteve.rentboot.security.srv.JwtService;
import com.oscaresteve.rentboot.srv.mapper.UsuarioMapper;

@Service
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UsuarioRepository usuarioRepository;
  private final RolRepository rolRepository;
  private final PasswordEncoder passwordEncoder;
  private final UsuarioMapper usuarioMapper = UsuarioMapper.INSTANCE;

  public AuthServiceImpl(
    AuthenticationManager authenticationManager,
    JwtService jwtService,
    UsuarioRepository usuarioRepository,
    RolRepository rolRepository,
    PasswordEncoder passwordEncoder
  ) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.usuarioRepository = usuarioRepository;
    this.rolRepository = rolRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public JwtDto login(LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );

    UserDetails principal = (UserDetails) authentication.getPrincipal();
    String token = jwtService.generateToken(principal);
    List<String> roles = principal.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .toList();
    return new JwtDto(token, "Bearer", principal.getUsername(), roles);
  }

  @Override
  @Transactional
  public UsuarioView register(RegisterRequest request) {
    if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
      throw new DomainException("USERNAME_ALREADY_EXISTS", "El username ya esta en uso");
    }

    UsuarioDb usuarioDb = new UsuarioDb();
    usuarioDb.setUsername(request.getUsername());
    usuarioDb.setPassword(passwordEncoder.encode(request.getPassword()));
    usuarioDb.setEnabled(request.getEnabled() == null ? Boolean.TRUE : request.getEnabled());
    usuarioDb.setRoles(resolveDefaultRole());

    UsuarioDb saved = usuarioRepository.save(usuarioDb);
    return usuarioMapper.UsuarioDbToUsuarioView(saved);
  }

  private Set<RolDb> resolveDefaultRole() {
    RolDb defaultRole = rolRepository.findByNombre("ROLE_USER")
      .orElseThrow(() -> new DomainException("ROLE_NOT_FOUND", "El rol por defecto ROLE_USER no existe"));
    return new HashSet<>(Set.of(defaultRole));
  }
}
