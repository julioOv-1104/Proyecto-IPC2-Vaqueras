import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EmpresaServie } from '../../servicios/empresa-service';
import { OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-agregar-recursos',
  imports: [FormsModule, CommonModule],
  templateUrl: './agregar-recursos.html',
  styleUrl: './agregar-recursos.css',
})
export class AgregarRecursos {

  constructor(private empresaService: EmpresaServie, private router: Router) { }

  mensajeError: String | null = null;
  almacenamiento: String = '';
  ram: String = '';
  procesador: String = '';
  sistema: String = '';
  juego: any


  ngOnInit() {
    const juegoSession = sessionStorage.getItem('juegoEnCreacion');
    if (juegoSession) {
      this.juego = JSON.parse(juegoSession);
    }
  }


  guardarRecursos() {

    this.empresaService.agregarRecursos(this.juego.titulo, this.almacenamiento, this.ram, this.procesador,
      this.sistema).subscribe({
        next: (response: any) => {

          // si recibe un error
          if (response.status === 'error') {
            this.mensajeError = response.mensaje;
            alert(this.mensajeError);
            return;
          }

          alert('recursos agregados a ' + this.juego.titulo)

        }
      });

      this.router.navigate(['/empresario']);
  }



}
