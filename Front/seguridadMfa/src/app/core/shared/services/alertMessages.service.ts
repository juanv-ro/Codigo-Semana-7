import { Injectable } from '@angular/core';
import Swal, { SweetAlertIcon } from 'sweetalert2';

@Injectable({
  providedIn: 'root',
})
export class AlertMessagesService {
  private showAlert(title: string, message: string, typeAlert: SweetAlertIcon) {
    return Swal.fire(title, message, typeAlert);
  }

  showSuccess(title: string, message: string) {
    return this.showAlert(title, message, 'success');
  }

  showInfo(title: string, message: string) {
    return this.showAlert(title, message, 'info');
  }

  showWarning(title: string, message: string) {
    return this.showAlert(title, message, 'warning');
  }

  showError(title: string, message: string) {
    return this.showAlert(title, message, 'error');
  }
}
