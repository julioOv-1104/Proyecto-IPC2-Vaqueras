import { Component } from '@angular/core';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { EmpresaServie } from '../../servicios/empresa-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Empresa } from '../../modelos/empresa';
import { OnInit } from '@angular/core';
import { AdminService } from '../../servicios/admin-service';

@Component({
  selector: 'app-vista-admin',
  imports: [FormsModule, CommonModule],
  templateUrl: './vista-admin.html',
  styleUrl: './vista-admin.css',
})
export class VistaAdmin {

  empresas: Empresa[] = [];
  comision: number = 0;

  constructor(private empresaService: EmpresaServie, private adminService: AdminService) { }

  router = inject(Router);

  ngOnInit(): void {
    this.empresaService.obtenerEmpresas().subscribe({
      next: (data) => {
        this.empresas = data;
      },
      error: (err) => {
        console.error('Error al obtener empresas', err);
      }
    });
  }

  cambiarEspecifica(empresa: Empresa) {

    if (this.comision < 0) {
      alert('Comision invalida');
      return
    }

    this.adminService.cambiarComisionEspecifica(empresa.nombre_empresa, this.comision)
      .subscribe({
        next: (res: any) => {
          alert(res.mensaje);
        },

      });

  }

  cambiarGlobal() {

    if (this.comision < 0) {
      alert('Comision invalida');
      return
    }

    this.adminService.cambiarComisionGlobal(this.comision)
      .subscribe({
        next: (res: any) => {
          alert(res.mensaje);
        },

      });

  }

  mostrarRegistroEmpresario() {

    this.router.navigate(['/registroEmpresario']);

  }

  mostrarRegistroEmpresa() {

    this.router.navigate(['/registroEmpresa']);

  }

  logout() {
    sessionStorage.removeItem('usuario');
    this.router.navigate(['/login']);
  }

}
