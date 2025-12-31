import { Injectable } from '@angular/core';
import { Empresa } from '../modelos/empresa';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cateogoria } from '../modelos/cateogoria';

@Injectable({
  providedIn: 'root',
})
export class EmpresaServie {

  private url = 'http://localhost:8080/ProyectoVaquerasIPC2/RegistroEmpresaServlet';
  private apiUrl = 'http://localhost:8080/ProyectoVaquerasIPC2/EmpresaServlet?accion=obtenerTodas';
  private categoriasURL = 'http://localhost:8080/ProyectoVaquerasIPC2/JuegoServlet?accion=obtenerCategorias';
  private agregarCategoriaURL = 'http://localhost:8080/ProyectoVaquerasIPC2/JuegoServlet?accion=agregarCategoria';
  private agregarRecursosURL ='http://localhost:8080/ProyectoVaquerasIPC2/JuegoServlet?accion=agregarRecursos';

  constructor(private http: HttpClient) { }

  registrarEmpresa(nombre_empresa: String, descripcion: String, comision: number) {
    return this.http.post(this.url, {
      nombre_empresa: nombre_empresa, descripcion: descripcion, comision: comision
    });
  }

  obtenerEmpresas(): Observable<Empresa[]> {
    return this.http.get<Empresa[]>(this.apiUrl);
  }

  

  obtenercategorias(): Observable<Cateogoria[]> {
    return this.http.get<Cateogoria[]>(this.categoriasURL);
  }

  cambiarCategoria(titulo: String, categoria: String) {
    return this.http.post(this.agregarCategoriaURL, {
      titulo: titulo, categoria: categoria
    });
  }

  agregarRecursos(titulo: String,almacenamiento: String, ram: String, procesador: String, sistema: String) {
    return this.http.post(this.agregarRecursosURL, {
      titulo:titulo, valor_almacenamiento: almacenamiento, valor_ram: ram, valor_procesador: procesador, valor_sistema: sistema
    });
  }

}
