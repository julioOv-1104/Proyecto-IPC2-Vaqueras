import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Juego } from '../modelos/juego';

@Injectable({
  providedIn: 'root',
})
export class JuegoService {


  private SESSION_KEY = 'juegoEnCreacion';

  guardarJuego(juego: Juego): void {
    const juegoNuevo = {
      titulo: juego.titulo
    };

    sessionStorage.setItem(
      this.SESSION_KEY,
      JSON.stringify(juegoNuevo)
    );
  }

  private apiUrl = 'http://localhost:8080/ProyectoVaquerasIPC2/JuegoServlet?accion=obtenerTodosLosJuegos';
  private urlParaCrear = 'http://localhost:8080/ProyectoVaquerasIPC2/JuegoServlet?accion=registrarJuego'

  constructor(private http: HttpClient) { }

  obtenerJuegos(): Observable<Juego[]> {
    return this.http.get<Juego[]>(this.apiUrl);
  }

  registrarJuego(juegoNuevo: Partial<Juego>, archivo: File, fecha: Date) {
    const formData = new FormData

    formData.append('nombre_empresa', (juegoNuevo.nombre_empresa || "").toString());
    formData.append('titulo', (juegoNuevo.titulo || '').toString());
    formData.append('precio', (juegoNuevo.precio || 0).toString());
    formData.append('clasificacion', (juegoNuevo.clasificacion || '').toString());
    formData.append('descripcion', (juegoNuevo.descripcion || '').toString());
    formData.append('fecha_lanzamiento', (fecha).toString());


    formData.append('multimedia', archivo);

    return this.http.post(this.urlParaCrear, formData);
  }


}
