import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CompraService } from '../../servicios/compra-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../servicios/usuario.service';
import { AuthService } from '../../servicios/auth.service';
import { Usuario } from '../../modelos/usuario';


@Component({
  selector: 'app-cartera',
  imports: [FormsModule, CommonModule],
  templateUrl: './cartera.html',
  styleUrl: './cartera.css',
})
export class Cartera {

  constructor(private router: Router, private compraService: CompraService,
    private usuarioService: UsuarioService, private authService: AuthService
  ){}

  usuario: any;
  cartera: number|null = null;
  cantidadRecarga: number = 0;
  correo_usuario = '';
  
  

  ngOnInit(): void {

    

    const usuarioSession = sessionStorage.getItem('usuario');
    if(usuarioSession){
      this.usuario = JSON.parse(usuarioSession);
      this.cartera = this.usuario.cartera;
      this.correo_usuario = this.usuario.correo_usuario;
    }
     
    console.log('usuario: '+this.usuario.correo_usuario);
   

  }

  iniciar(){
    this.usuarioService.obtenerUsuario(this.correo_usuario).subscribe({
      next: (response: any) => {

        // si recibe un usuario
        const usuario = response as Usuario;
        sessionStorage.setItem('usuario', JSON.stringify(response));
      }
    });


  }

  recargar(){

const correo_usuario = this.usuario.correo_usuario;
const recarga = this.cantidadRecarga;

if(recarga<0){
  alert('cantidad invalida');
  return;
}

    this.compraService.recargar(correo_usuario, recarga)
      .subscribe({
        next: (res) => alert('¡Recarga exitosa!'),
    error: (err) => alert('El servidor rechazó la recarga')

      });
  }

   regresar() {
    
    this.router.navigate(['/tienda']);
  }

}
