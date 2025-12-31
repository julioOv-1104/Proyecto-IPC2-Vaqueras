import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Empresa } from '../../modelos/empresa';
import { CommonModule } from '@angular/common';
import { EmpresaServie } from '../../servicios/empresa-service';


@Component({
  selector: 'app-registro-empresa',
  imports: [FormsModule, CommonModule],
  templateUrl: './registro-empresa.html',
  styleUrl: './registro-empresa.css',
})
export class RegistroEmpresa {

  nombre_empresa: String = new String
  descripcion: String = new String
  comision: number = 15;

  mensajeError: string | null = null;

  constructor(private empresaService: EmpresaServie) { }

  router = inject(Router);

  imprimir() {
    console.log('nombre de la empresa: ' + this.nombre_empresa + ', descripcion: ' + this.descripcion)
  }

  registrarEmpresa() {

    if (this.comision <= 0 || this.descripcion === '' || this.nombre_empresa === '') {
      alert('revice sus datos')
      return
    }

    this.empresaService.registrarEmpresa(this.nombre_empresa, this.descripcion, this.comision).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          alert(this.mensajeError);
          return;
        }

        alert('empresa registrada con exito')

      }
    });

  }


  regresar() {

    this.router.navigate(['/admin']);

  }
}
