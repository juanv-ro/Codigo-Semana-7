import { Component, inject } from '@angular/core';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

import { RequestGenerateCodeMfa, ResponseGenerateCodeMfa } from '../core/shared/models/generateCodeMfa';
import { RequestValidateCodeMfa } from '../core/shared/models/validateCodeMfa';
import { AlertMessagesService } from '../core/shared/services/alertMessages.service';
import { CodigoMfaService } from '../core/shared/services/codigoMfa.service';
import { MultiFactorService } from '../core/shared/services/multiFactor.service';

@Component({
  selector: 'datatools-mf-auntenticacion',
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, MatButtonModule, ReactiveFormsModule],
  templateUrl: './mfAuntenticacion.component.html',
  styleUrl: './mfAuntenticacion.component.scss',
})
export class MfAutenticacionComponent {
  private multiFactorService = inject(MultiFactorService);
  private codigoMfaService = inject(CodigoMfaService);
  private alertService = inject(AlertMessagesService);

  public correo: string = '';
  public formCodigoMfa = new FormControl('', [Validators.required]);
  public formCorreo = new FormControl('', [Validators.required]);
  private idCodigo: string = '';
  public mensaje: string = '';

  constructor() {}

  generarCodigo() {
    const request: RequestGenerateCodeMfa = {
      correo: this.formCorreo.value || '',
      asunto: 'Codigo de autenticacion',
    };

    this.codigoMfaService.generarCodigoMfa(request).subscribe({
      next: (response) => {
        const body: ResponseGenerateCodeMfa = response.body;
        this.idCodigo = response.headers.get('id-codigo') || '';
        this.mensaje = body.descripcionRespuesta;
        this.alertService.showError('Error', body.descripcionRespuesta);
      },
      error: (error) => this.alertService.showError('Error', error.error.descripcionRespuesta),
    });
  }

  validarCodigo() {
    const requestValidarCodigo: RequestValidateCodeMfa = {
      codigo: this.formCodigoMfa.value || '',
    };

    this.codigoMfaService.validarCodigoMfa(this.idCodigo, requestValidarCodigo).subscribe({
      next: (response) => {
        if (response.codigoRespuesta == '000') {
          this.alertService.showSuccess('Validado', response.descripcionRespuesta).then(() => {
            this.multiFactorService.sendData(true);
          });
        }
      },
      error: (error) => {
        console.log(error.error.descripcionRespuesta);
        this.alertService.showError('Error', error.error.descripcionRespuesta);
      },
    });
  }

  hideEmail(email: string): string {
    const [username, domain] = email.split('@');
    const hiddenUsername = username.slice(0, 2) + '*'.repeat(username.length - 2);
    return `${hiddenUsername}@${domain}`;
  }
}
