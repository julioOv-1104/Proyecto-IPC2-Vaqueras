import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { inject } from '@angular/core';
import { Usuario } from '../../modelos/usuario';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../servicios/usuario.service';
import { AuthService } from '../../servicios/auth.service';

@Component({
  selector: 'app-registro-empresario',
  imports: [FormsModule, CommonModule],
  templateUrl: './registro-empresario.html',
  styleUrl: './registro-empresario.css',
})
export class RegistroEmpresario {

  usuario: any

  contrasenna = '';
  correo_usuario = '';
  nombre = '';
  nombre_empresa = '';
  fecha_nacimiento = new Date;
  tipo_usuario = 'EMPRESARIO';
  mensajeError: String | null = null;

  constructor(private usuarioService: UsuarioService,
    private authService: AuthService) { }

  router = inject(Router);

  ngOnInit(): void {

    const usuarioSession = sessionStorage.getItem('usuario');
    if (usuarioSession) {
      this.usuario = JSON.parse(usuarioSession);
    }

  }


  RegistrarUsuarioEmpresario() {

    this.mensajeError = null; // limpiar errores anteriores

    const usuarioNuevo = {
      contrasenna: this.contrasenna,
      correo_usuario: this.correo_usuario,
      tipo_usuario: this.tipo_usuario,
      nombre: this.nombre,
      nombre_empresa: this.nombre_empresa,
      fecha_nacimiento: this.fecha_nacimiento
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
        this.router.navigate(['/registroEmpresario']);//regresa a la vista del formulario
      }


    });

  }


  regresar() {

    if (this.usuario.tipo_usuario === 'EMPRESARIO') {
      this.router.navigate(['/empresario'])
    } else {
      this.router.navigate(['/admin']);
    }



  }

}
