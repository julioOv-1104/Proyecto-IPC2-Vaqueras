import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Usuario } from '../../modelos/usuario';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../servicios/usuario.service';
import { AuthService } from '../../servicios/auth.service';

@Component({
  selector: 'app-registro-comun',
  imports: [FormsModule, CommonModule],
  templateUrl: './registro-comun.html',
  styleUrl: './registro-comun.css',
})

export class RegistroComun {

  nickname = '';
  contrasenna = '';
  correo_usuario = '';
  telefono = '';
  pais = '';
  tipo_usuario = 'USUARIO_COMUN';
  fecha_nacimiento = new Date
  mensajeError: string | null = null;

  constructor(private usuarioService: UsuarioService,
    private authService: AuthService
  ) { }

  router = inject(Router);

  RegistrarUsuarioComun() {

    this.mensajeError = null; // limpiar errores anteriores

    const usuarioNuevo = {
      nickname: this.nickname,
      contrasenna: this.contrasenna,
      fecha_nacimiento: this.fecha_nacimiento,
      correo_usuario: this.correo_usuario,
      telefono: this.telefono,
      pais: this.pais,
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

  regresar() {

    this.router.navigate(['/registro']);

  }



}
