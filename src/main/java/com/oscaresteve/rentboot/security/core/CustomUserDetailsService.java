package com.oscaresteve.rentboot.security.core;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oscaresteve.rentboot.model.db.UsuarioDb;
import com.oscaresteve.rentboot.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UsuarioRepository usuarioRepository;

  public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UsuarioDb usuario = usuarioRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

    List<SimpleGrantedAuthority> authorities = usuario.getRoles().stream()
      .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
      .toList();

    return User.builder()
      .username(usuario.getUsername())
      .password(usuario.getPassword())
      .authorities(authorities)
      .disabled(!Boolean.TRUE.equals(usuario.getEnabled()))
      .build();
  }
}
