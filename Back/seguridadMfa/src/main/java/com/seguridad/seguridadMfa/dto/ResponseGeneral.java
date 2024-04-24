package com.seguridad.seguridadMfa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseGeneral {

	private String codigoRespuesta;
	private String descripcionRespuesta;

	public ResponseGeneral() {

	}

	public ResponseGeneral(String codigoRespuesta, String descripcionRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
		this.descripcionRespuesta = descripcionRespuesta;
	}
}
