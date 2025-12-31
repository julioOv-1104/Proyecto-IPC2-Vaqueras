import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CompraService {

  private url = 'http://localhost:8080/ProyectoVaquerasIPC2/CompraServlet';

  constructor(private http: HttpClient) {}

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