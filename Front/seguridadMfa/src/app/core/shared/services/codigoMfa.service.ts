import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { RequestGenerateCodeMfa, ResponseGenerateCodeMfa } from '../models/generateCodeMfa';
import { RequestValidateCodeMfa, ResponseValidateCodeMfa } from '../models/validateCodeMfa';

@Injectable({
  providedIn: 'root',
})
export class CodigoMfaService {
  private http = inject(HttpClient);

  generarCodigoMfa(request: RequestGenerateCodeMfa): Observable<HttpResponse<any>> {
    return this.http.post<HttpResponse<any>>(environment.seguridadRest + 'generateCodeMfa', request, { observe: 'response' });
  }

  validarCodigoMfa(idCodigo: string, request: RequestValidateCodeMfa): Observable<ResponseValidateCodeMfa> {
    const headers = new HttpHeaders({
      'id-codigo': idCodigo,
    });

    return this.http.post<ResponseGenerateCodeMfa>(environment.seguridadRest + 'validateCodeMfa', request, { headers });
  }
}
