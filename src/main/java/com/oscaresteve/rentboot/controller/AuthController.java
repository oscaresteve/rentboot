package com.oscaresteve.rentboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oscaresteve.rentboot.exception.CustomErrorResponse;
import com.oscaresteve.rentboot.model.dto.usuario.UsuarioView;
import com.oscaresteve.rentboot.security.dto.JwtDto;
import com.oscaresteve.rentboot.security.dto.LoginRequest;
import com.oscaresteve.rentboot.security.dto.RegisterRequest;
import com.oscaresteve.rentboot.security.srv.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Operaciones de autenticacion y registro")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  // Login
  @PostMapping("/login")
  @SecurityRequirements
  @Operation(summary = "Login", description = "Autentica usuario y devuelve JWT")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Login correcto", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = JwtDto.class)) }),
    @ApiResponse(responseCode = "401", description = "Credenciales invalidas", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
    @ApiResponse(responseCode = "400", description = "Datos invalidos", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
  })
  public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  // Register
  @PostMapping("/register")
  @SecurityRequirements
  @Operation(summary = "Registro", description = "Registra un nuevo usuario")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Usuario registrado", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioView.class)) }),
    @ApiResponse(responseCode = "400", description = "Datos invalidos", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
  })
  public ResponseEntity<UsuarioView> register(@Valid @RequestBody RegisterRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
  }
}
