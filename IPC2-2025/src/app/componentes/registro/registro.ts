import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { inject } from '@angular/core';

@Component({
  selector: 'app-registro',
  imports: [],
  templateUrl: './registro.html',
  styleUrl: './registro.css',
})
export class Registro {

   router = inject(Router);

   mostrarRegistroFormComun(){

    this.router.navigate(['/registroComun']);

   }

   mostrarRegistroFormAdmin(){

    this.router.navigate(['/registroAdmin']);

   }


   regresar(){

    this.router.navigate(['/login']);

   }

}
