import { Routes } from '@angular/router';
import { MfAutenticacionComponent } from './mfAuntenticacion/mfAuntenticacion.component';

export const routes: Routes = [
  { path: '', redirectTo: 'mfa', pathMatch: 'full' },
  { path: 'mfa', component: MfAutenticacionComponent },
];
