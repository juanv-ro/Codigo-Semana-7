package com.seguridad.seguridadMfa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seguridad.seguridadMfa.entities.CodigoMfa;

public interface CodigoMfaRepository extends JpaRepository<CodigoMfa, Long> {

}
