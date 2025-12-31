import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { Router } from '@angular/router';


export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
   

  // Leer sesión
  const usuario = sessionStorage.getItem('usuario');

  //  Si NO hay sesión → login
  if (!usuario) {
    router.navigate(['/login']);
    return false;
  }

  //  Si hay sesión permitir acceso
  return true;
};
