package com.oscaresteve.rentboot.model.db;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDb {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String nombre;

  @Column(nullable = false, length = 150, unique = true)
  private String email;

  @Column(length = 20)
  private String telefono;

  @Column(name = "fecha_registro", nullable = false)
  private LocalDateTime fechaRegistro = LocalDateTime.now();

  //Un Cliente tiene Muchos Alquileres
  @OneToMany(mappedBy = "cliente")
  private List<AlquilerDb> alquileres;
}
