import { Component } from '@angular/core';
import { UsuarioService } from '../../servicios/usuario.service';
import { Usuario } from '../../modelos/usuario';
import { AuthService } from '../../servicios/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login-form.html',
  styleUrl: './login-form.css',
})
export class LoginForm {

  correo_usuario = '';
  contrasenna = '';
  mensajeError: string | null = null;

  constructor(private usuarioService: UsuarioService,
    private authService: AuthService, private router: Router
  ) { }

  login() {

    console.log('login ejecutado con exito')

    this.mensajeError = null; // limpiar errores anteriores

    const usuarioLogin = {
      correo_usuario: this.correo_usuario,
      contrasenna: this.contrasenna
    };

    this.usuarioService.login(usuarioLogin).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        // si recibe un usuario
        const usuario = response as Usuario;

        this.authService.guardarUsuario(usuario);

        sessionStorage.setItem('usuario', JSON.stringify(response));
        console.log('Login correcto, usuario guardado en sesion:', sessionStorage.getItem('usuario'));


        switch (response.tipo_usuario) {
          case 'ADMIN': this.router.navigate(['/admin']); break;
          case 'EMPRESARIO': this.router.navigate(['empresario']); break;
          default: this.router.navigate(['/tienda']);
        }



      }
    });

  }

}
