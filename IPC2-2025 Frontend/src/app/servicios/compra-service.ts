import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Compras } from '../modelos/compras';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CompraService {

  private url = 'http://localhost:8080/ProyectoVaquerasIPC2/CompraServlet';
  private urlCompra = 'http://localhost:8080/ProyectoVaquerasIPC2/CompraServlet?correo_usuario'

  constructor(private http: HttpClient) {}

  obtenerBiblioteca(correo_usuario: string): Observable<Compras[]> {
    return this.http.get<Compras[]>(this.urlCompra, {
      params: {
        correo_usuario: correo_usuario || ''
      }
    });
  }

  getComprasPorCorreo(correo: string) {
  return this.http.get<any[]>(
    `http://localhost:8080/ProyectoVaquerasIPC2/ComprasServlet?correo_usuario=${correo}`
  );
}

  comprar(correo_usuario: String, titulo: String, fecha_compra: Date) {
    return this.http.post(this.url, {
      correo_usuario: correo_usuario,
      titulo: titulo, fecha_compra: fecha_compra
    });
  
}


  
  

 recargar(correo_usuario: String, recarga: number) {
    return this.http.put(this.url, {
      correo_usuario: correo_usuario,
      recarga: recarga
    });
  
}
}