import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { JuegoService } from '../../servicios/juegoService';
import { Juego } from '../../modelos/juego';
import { CompraService } from '../../servicios/compra-service';

@Component({
  selector: 'app-tienda',
  imports: [CommonModule, FormsModule],
  templateUrl: './tienda.html',
  styleUrl: './tienda.css',
})
export class Tienda {

  juegos: Juego[] = [];



  constructor(private router: Router, private juegoService: JuegoService,
    private compraService: CompraService) { }

  fecha: Date = new Date;
  fechaString = ''; 
  usuario: any;

 


  comprar(juego: Juego) {
    const formatoValido = /^\d{4}-\d{2}-\d{2}$/;

    this.fechaString = this.fecha.toString();

    if (!formatoValido.test(this.fechaString)) {
      alert('ingrese la fecha de compra');
      return;
    }

    const correo_usuario = this.usuario.correo_usuario;
    const fecha_compra = this.fecha;

    this.compraService.comprar(correo_usuario, juego.titulo, fecha_compra)
      .subscribe({
        next: (res: any) => {
          alert(res.mensaje);
        },

      });
  }


  logout() {
    sessionStorage.removeItem('usuario');
    this.router.navigate(['/login']);
  }

  irArecargar() {
    
    this.router.navigate(['/cartera']);
  }

  irAbiblioteca() {
    
    this.router.navigate(['/biblioteca']);
  }

  comprarJuego(juego: Juego) {
    console.log('juego seleccionado ' + juego.titulo + ' ' + this.fecha + ' ' + this.usuario.nickname);

  }

  ngOnInit(): void {

    this.juegoService.obtenerJuegos().subscribe({
      next: (data) => {
        this.juegos = data;
      },
      error: (err) => {
        console.error('Error al obtener juegos', err);
      }
    });

    const usuarioSession = sessionStorage.getItem('usuario');
    if (usuarioSession) {
      this.usuario = JSON.parse(usuarioSession);
    }
    
  }

}
