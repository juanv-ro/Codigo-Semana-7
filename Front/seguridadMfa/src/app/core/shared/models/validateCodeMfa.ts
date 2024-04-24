export interface RequestValidateCodeMfa {
  codigo: string;
}

export interface ResponseValidateCodeMfa {
  codigoRespuesta: string;
  descripcionRespuesta: string;
}
