package com.seguridad.seguridadMfa.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ValidarCodigoMfa {

    @NotNull(message = "El codigo no puede ser nulo")
    @NotEmpty(message = "El codigo no puede estar vacio")
    private String codigo;
}
