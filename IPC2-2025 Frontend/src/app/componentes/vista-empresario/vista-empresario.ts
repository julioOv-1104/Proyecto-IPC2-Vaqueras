import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-vista-empresario',
  imports: [],
  templateUrl: './vista-empresario.html',
  styleUrl: './vista-empresario.css',
})
export class VistaEmpresario {

   router = inject(Router);

    logout(){
    sessionStorage.removeItem('usuario');
    this.router.navigate(['/login']);
  }

  registrarJuego(){
    this.router.navigate(['registroJuego']);
  }

  registrarEmpresario(){
    this.router.navigate(['/registroEmpresario']);
  }

}
