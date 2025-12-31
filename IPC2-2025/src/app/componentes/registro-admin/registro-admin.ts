import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { inject } from '@angular/core';
import { Usuario } from '../../modelos/usuario';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../servicios/usuario.service';
import { AuthService } from '../../servicios/auth.service';

@Component({
  selector: 'app-registro-admin',
  imports: [FormsModule, CommonModule],
  templateUrl: './registro-admin.html',
  styleUrl: './registro-admin.css',
})
export class RegistroAdmin {

  contrasenna = '';
  correo_usuario = '';
  tipo_usuario = 'ADMIN';
  mensajeError: String | null = null;

  constructor(private usuarioService: UsuarioService, 
    private authService: AuthService){}

  router = inject(Router);


  RegistrarUsuarioAdmin() {
  
      this.mensajeError = null; // limpiar errores anteriores
  
      const usuarioNuevo = {
        contrasenna: this.contrasenna,
        correo_usuario: this.correo_usuario,
        tipo_usuario: this.tipo_usuario
      };
  
      this.usuarioService.registrar(usuarioNuevo).subscribe({
        next: (response: any) => {
  
          // si recibe un error
          if (response.status === 'error') {
            this.mensajeError = response.mensaje;
            return;
          }
  
          // si recibe un usuario, es porque creo un usuario
          const usuario = response as Usuario;
          this.router.navigate(['/login']);
        }
  
  
      });
  
    }


regresar(){

    this.router.navigate(['/registro']);

   }

}
