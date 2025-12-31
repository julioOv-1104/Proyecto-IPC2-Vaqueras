import { CanActivateFn } from '@angular/router';
import { Router } from '@angular/router';
import { inject } from '@angular/core';

export const rolGuard: CanActivateFn = (route, state) => {
  
  
  const router = inject(Router);

   const usuarioSession = sessionStorage.getItem('usuario');

  // Si no hay sesión → login
  if (!usuarioSession) {
    router.navigate(['/login']);
    return false;
  }

  const usuario = JSON.parse(usuarioSession);

  const rolEsperado = route.data['rol'];

  // Si el rol no coincide  redirigir
  if (usuario.tipo_usuario !== rolEsperado) {
    router.navigate(['/tienda']);
    return false;
  }

  return true;


};
