package com.oscaresteve.rentboot.model.db;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alquiler")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlquilerDb {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "fecha_inicio", nullable = false)
  private LocalDate fechaInicio;

  @Column(name = "fecha_fin", nullable = false)
  private LocalDate fechaFin;

  @Column(name = "precio_total", nullable = false)
  private BigDecimal precioTotal;

  //Muchos alquileres pueden tener un cliente
  @ManyToOne
  @JoinColumn(name = "cliente_id", nullable = false)
  private ClienteDb cliente;

  //Muchos alquileres pueden tener un vehiculo
  @ManyToOne
  @JoinColumn(name = "vehiculo_id", nullable = false)
  private VehiculoDb vehiculo;
}
