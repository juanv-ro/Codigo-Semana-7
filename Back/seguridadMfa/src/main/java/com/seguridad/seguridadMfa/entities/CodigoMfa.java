package com.seguridad.seguridadMfa.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "codigo_mfa")
@NoArgsConstructor
@Getter
@Setter
public class CodigoMfa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false)
    private String codigo;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "fecha_hora", nullable = true)
    private LocalDateTime fechaHora;
}
