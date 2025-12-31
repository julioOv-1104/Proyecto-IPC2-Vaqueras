import { CanActivateFn } from '@angular/router';
import { Router } from '@angular/router';
import { inject } from '@angular/core';

export const loginGuard: CanActivateFn = (route, state) => {

const router = inject(Router);

const usuario =sessionStorage.getItem('usuario');

if(usuario){
router.navigate(['/tienda']);
return false;
}

  return true;
};
