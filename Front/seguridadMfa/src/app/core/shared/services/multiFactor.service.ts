import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MultiFactorService {
  private mfaSubject = new Subject<boolean>();

  sendData(data: boolean) {
    this.mfaSubject.next(data);
  }

  verificacionCodigo(): Observable<boolean> {
    return this.mfaSubject;
  }
}
