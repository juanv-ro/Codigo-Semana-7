package com.seguridad.seguridadMfa.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.seguridad.seguridadMfa.dto.GenerarCodigoMfa;
import com.seguridad.seguridadMfa.dto.ResponseGeneral;
import com.seguridad.seguridadMfa.dto.ValidarCodigoMfa;
import com.seguridad.seguridadMfa.entities.CodigoMfa;
import com.seguridad.seguridadMfa.repositories.CodigoMfaRepository;

@Service
public class CodigoMfaService {

    private final static Logger logger = Logger.getLogger(CodigoMfaService.class.getName());

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Autowired
    AsyncService asyncService;

    @Autowired
    CodigoMfaRepository codigoMfaRepository;

    public ResponseEntity<?> generarCodigo(GenerarCodigoMfa request) {

        String code;
        ResponseGeneral response = new ResponseGeneral();

        try {
            code = generateRandomCode(6);

            // Guardar codigo en tabla y devolver id por header
            CodigoMfa codigoMfa = new CodigoMfa();
            codigoMfa.setCodigo(code);
            codigoMfa.setCorreo(request.getCorreo());
            codigoMfa.setFechaHora(LocalDateTime.now());
            codigoMfaRepository.save(codigoMfa);

            HttpHeaders headers = new HttpHeaders();
            headers.add("id-codigo", codigoMfa.getId().toString());
            headers.add("Access-Control-Expose-Headers", "id-codigo");

            boolean responseCorreo = asyncService.enviarCorreo(request, code);

            if (responseCorreo) {
                response.setCodigoRespuesta("000");
                response.setDescripcionRespuesta("Se ha enviado un correo con el codigo de seguridad.");
                return new ResponseEntity<>(response, headers, HttpStatus.OK);
            } else {
                response.setCodigoRespuesta("999");
                response.setDescripcionRespuesta(
                        "Se genero el codigo de seguridad. Ocurrio un problema enviando el correo.");
                return new ResponseEntity<>(response, headers, HttpStatus.OK);
            }

        } catch (Exception e) {
            logger.severe("Error al generar codigo de seguridad: " + e);
            response.setCodigoRespuesta("999");
            response.setDescripcionRespuesta("Error generando codigo de seguridad.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> checkCode(ValidarCodigoMfa request, String idCodigo) {

        ResponseGeneral response = new ResponseGeneral();

        try {
            // Consultar codigo en tabla por header y validar codigo
            CodigoMfa codigoMfa = codigoMfaRepository.findById(Long.parseLong(idCodigo)).orElse(null);

            if (codigoMfa == null) {
                response.setCodigoRespuesta("999");
                response.setDescripcionRespuesta("Peticion no valida.");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (hasPassed5Minutes(codigoMfa.getFechaHora())) {
                response.setCodigoRespuesta("999");
                response.setDescripcionRespuesta("El codigo ha expirado.");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (request.getCodigo().trim().equals(codigoMfa.getCodigo())) {
                response.setCodigoRespuesta("000");
                response.setDescripcionRespuesta("Codigo validado Exitosamente.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setCodigoRespuesta("999");
                response.setDescripcionRespuesta("Error verificando codigo de seguridad. El codigo no es valido.");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            logger.severe("Error al verificar codigo de seguridad: " + e);
            response.setCodigoRespuesta("999");
            response.setDescripcionRespuesta("Error verificando codigo de seguridad.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateRandomCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }

    public static boolean hasPassed5Minutes(LocalDateTime timestamp) {
        LocalDateTime now = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(timestamp, now);
        return minutes >= 1;
    }
}
