package com.seguridad.seguridadMfa.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GenerarCodigoMfa {

    @NotNull(message = "El correo no puede ser nulo")
    @NotEmpty(message = "El correo no puede estar vacio")
    private String correo;

    @NotNull(message = "El asunto no puede ser nulo")
    @NotEmpty(message = "El asunto no puede estar vacio")
    private String asunto;
}
