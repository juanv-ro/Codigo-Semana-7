package com.seguridad.seguridadMfa.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seguridad.seguridadMfa.dto.GenerarCodigoMfa;
import com.seguridad.seguridadMfa.dto.ResponseGeneral;
import com.seguridad.seguridadMfa.dto.ValidarCodigoMfa;
import com.seguridad.seguridadMfa.services.CodigoMfaService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping("/SeguridadRest/")
@RestController
public class SeguridadRest {

    @Autowired
    CodigoMfaService codigoMfaService;

    @RequestMapping(value = "/generateCodeMfa", method = RequestMethod.POST)
    public ResponseEntity<?> generateCodeMfa(@RequestHeader Map<String, String> parametros,
            @Valid @RequestBody GenerarCodigoMfa request, BindingResult result) {

        if (result.hasErrors()) {
            ResponseGeneral response = new ResponseGeneral();
            response.setCodigoRespuesta("999");
            response.setDescripcionRespuesta(result.getFieldError().getDefaultMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return codigoMfaService.generarCodigo(request);
    }

    @RequestMapping(value = "/validateCodeMfa", method = RequestMethod.POST)
    public ResponseEntity<?> validateCodeMfa(@RequestHeader Map<String, String> parametros,
            @Valid @RequestBody ValidarCodigoMfa request, BindingResult result,
            @RequestHeader("id-codigo") String idCodigo) {

        if (result.hasErrors()) {
            ResponseGeneral response = new ResponseGeneral();
            response.setCodigoRespuesta("999");
            response.setDescripcionRespuesta(result.getFieldError().getDefaultMessage());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return codigoMfaService.checkCode(request, idCodigo);
    }

}
