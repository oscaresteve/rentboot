package com.oscaresteve.rentboot.model.db;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehiculo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDb {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String marca;

  @Column(nullable = false, length = 100)
  private String modelo;

  @Column(nullable = false, length = 20, unique = true)
  private String matricula;

  @Column(name = "precio_por_dia", nullable = false)
  private BigDecimal precioPorDia;

  @Column(nullable = false)
  private Boolean disponible = true;

  //Los vehiculos solo pueden tener una categoria
  @ManyToOne
  @JoinColumn(name = "categoria_id", nullable = false)
  private CategoriaDb categoria;

  //Un vehiculo puede tener muchos alquileres
  @OneToMany(mappedBy = "vehiculo")
  private List<AlquilerDb> alquileres;
}
