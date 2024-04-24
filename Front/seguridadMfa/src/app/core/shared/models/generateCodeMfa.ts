export interface RequestGenerateCodeMfa {
  correo: string;
  asunto: string;
}

export interface ResponseGenerateCodeMfa {
  codigoRespuesta: string;
  descripcionRespuesta: string;
}
