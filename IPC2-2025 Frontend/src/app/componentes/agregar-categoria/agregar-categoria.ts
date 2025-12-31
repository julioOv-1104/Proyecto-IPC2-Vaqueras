import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Juego } from '../../modelos/juego';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmpresaServie } from '../../servicios/empresa-service';
import { Cateogoria } from '../../modelos/cateogoria';
import { Router } from '@angular/router';

@Component({
  selector: 'app-agregar-categoria',
  imports: [CommonModule, FormsModule],
  templateUrl: './agregar-categoria.html',
  styleUrl: './agregar-categoria.css',
})
export class AgregarCategoria {

constructor(private empresaService: EmpresaServie, private router: Router){}

  juego: any;
  categorias: Cateogoria[] = [];
  nuevaCategoria: String = '';
  mensajeError: String | null = null;

  juegoNuevo: string = '';

  ngOnInit() {

    this.empresaService.obtenercategorias().subscribe({
      next: (data) => {
        this.categorias = data;
      },
      error: (err) => {
        console.error('Error al obtener categorias', err);
      }
    });

    const juegoSession = sessionStorage.getItem('juegoEnCreacion');
    if (juegoSession) {
      this.juego = JSON.parse(juegoSession);
    }
  }

  irArecursos(){
    this.router.navigate(['recursos']);
  }

  agregarCategoria(){

console.log('categoria: '+this.nuevaCategoria);

    this.empresaService.cambiarCategoria(this.juego.titulo, this.nuevaCategoria).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          alert(this.mensajeError);
          return;
        }

        alert('categoria agregada a '+this.juego.titulo)

      }
    });
  }

}
