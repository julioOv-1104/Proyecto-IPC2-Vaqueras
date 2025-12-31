import { Component } from '@angular/core';
import { CompraService } from '../../servicios/compra-service';
import { Compras } from '../../modelos/compras';
import { Usuario } from '../../modelos/usuario';
import { OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-biblioteca',
  imports: [FormsModule, CommonModule],
  templateUrl: './biblioteca.html',
  styleUrl: './biblioteca.css',
})
export class Biblioteca {

  compras: any[]= [];
  usuario: any

  constructor(private compraService: CompraService){}

  prueba(): void {

    const usuarioSession = sessionStorage.getItem('usuario');
    if (usuarioSession) {
      this.usuario = JSON.parse(usuarioSession);
    }

    this.compraService.obtenerBiblioteca(this.usuario.correo_usuario).subscribe({
      next: (data) => {
        this.compras = data;
      },
      error: (err) => {
        console.error('Error al obtener las compras', err);
      }
    });
    
    
  }

  ngOnInit() {
  const usuario = JSON.parse(sessionStorage.getItem('usuario')!);

  this.compraService
    .getComprasPorCorreo(usuario.correo_usuario)
    .subscribe({
      next: (data) => {
        this.compras = data;
        console.log(this.compras);
      },
      error: () => {
        alert('Error al obtener compras');
      }
    });
}

}
