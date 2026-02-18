package com.oscaresteve.rentboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.oscaresteve.rentboot.security.core.AuthEntryPoint;
import com.oscaresteve.rentboot.security.core.CustomAccessDeniedHandler;
import com.oscaresteve.rentboot.security.core.CustomUserDetailsService;
import com.oscaresteve.rentboot.security.jwt.JwtAuthFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;
  private final AuthEntryPoint authEntryPoint;
  private final CustomAccessDeniedHandler accessDeniedHandler;
  private final CustomUserDetailsService userDetailsService;

  public SecurityConfig(
    JwtAuthFilter jwtAuthFilter,
    AuthEntryPoint authEntryPoint,
    CustomAccessDeniedHandler accessDeniedHandler,
    CustomUserDetailsService userDetailsService
  ) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.authEntryPoint = authEntryPoint;
    this.accessDeniedHandler = accessDeniedHandler;
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .exceptionHandling(ex -> ex
        .authenticationEntryPoint(authEntryPoint)
        .accessDeniedHandler(accessDeniedHandler)
      )
      .authenticationProvider(authenticationProvider())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(
          "/api/auth/**",
          "/swagger-rentboot-ui/**",
          "/api-docs/**",
          "/v3/api-docs/**",
          "/swagger-ui/**",
          "/swagger-ui.html"
        ).permitAll()
        // Gestion de seguridad y usuarios
        .requestMatchers("/api/usuarios/**", "/api/roles/**").hasRole("ADMIN")
        // Lectura operativa para usuarios autenticados
        .requestMatchers(HttpMethod.GET, "/api/categorias/**", "/api/vehiculos/**", "/api/clientes/**", "/api/alquileres/**")
          .hasAnyRole("USER", "ADMIN")
        // Operaciones de catalogo y clientes reservadas a admin
        .requestMatchers(HttpMethod.POST, "/api/categorias/**", "/api/vehiculos/**", "/api/clientes/**")
          .hasRole("ADMIN")
        .requestMatchers(HttpMethod.PUT, "/api/categorias/**", "/api/vehiculos/**", "/api/clientes/**")
          .hasRole("ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/api/categorias/**", "/api/vehiculos/**", "/api/clientes/**")
          .hasRole("ADMIN")
        // Alquileres: alta y consulta para USER/ADMIN, cambios destructivos para ADMIN
        .requestMatchers(HttpMethod.POST, "/api/alquileres/**").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.PUT, "/api/alquileres/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/api/alquileres/**").hasRole("ADMIN")
        .anyRequest().authenticated()
      )
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
