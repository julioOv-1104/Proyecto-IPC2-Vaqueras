import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AdminService {

    constructor(private http: HttpClient) { }

  private url2 = 'http://localhost:8080/ProyectoVaquerasIPC2/AdminServlet?accion=comisionEspecifica';
  private urlGlobal = 'http://localhost:8080/ProyectoVaquerasIPC2/AdminServlet?accion=comisionGlobal';

  cambiarComisionEspecifica(nombre_empresa: String, comision: number) {
    return this.http.put(this.url2, {
      nombre_empresa: nombre_empresa, comision: comision
    });
  }

  cambiarComisionGlobal( comision: number) {
    return this.http.put(this.urlGlobal, {
       comision: comision
    });
  }

}
