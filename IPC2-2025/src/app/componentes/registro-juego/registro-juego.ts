import { Component, Inject } from '@angular/core';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { EmpresaServie } from '../../servicios/empresa-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Empresa } from '../../modelos/empresa';
import { OnInit } from '@angular/core';
import { JuegoService } from '../../servicios/juegoService';
import { Juego } from '../../modelos/juego';

@Component({
  selector: 'app-registro-juego',
  imports: [FormsModule, CommonModule],
  templateUrl: './registro-juego.html',
  styleUrl: './registro-juego.css',
})

export class RegistroJuego {

  constructor(private juegoService: JuegoService) { }

  router = inject(Router)

  titulo = ''
  precio: number = 0
  clasificacion = ''
  fecha: Date = new Date
  nombre_empresa = ''
  descripcion = ''
  mensajeError: String | null = null
  multimedia: File | null = null;

  regresar() {
    this.router.navigate(['/empresario']);
  }

  imagenSeleccionada(event: any) {
    const file: File = event.target.files[0];
    if (file) {
        this.multimedia = file;
    }

    if (this.multimedia && this.multimedia.type !== 'image/jpeg') {
      alert('Solo se permiten imágenes JPEG');
      this.multimedia = null;
    }
  }

  registrarJuegoNuevo() {

    const juegoNuevo = {
      titulo: this.titulo,
      precio: this.precio,
      clasificacion: this.clasificacion,
      fecha: this.fecha,
      nombre_empresa: this.nombre_empresa,
      descripcion: this.descripcion,
      Imagen: this.multimedia

    };



    if(juegoNuevo.Imagen){
      this.juegoService.registrarJuego(juegoNuevo,juegoNuevo.Imagen, juegoNuevo.fecha).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          alert(response.mensaje)
          return;
        }

        // si recibe un usuario, es porque creo un usuario
        const juego = response as Juego;
        this.juegoService.guardarJuego(juego);
        alert('jugo registrado con exito')
        this.router.navigate(['categoria']);


      }

    });
    }
  }

}
