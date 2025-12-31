import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../modelos/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private url = 'http://localhost:8080/ProyectoVaquerasIPC2/LoginServlet';
  private url2 = 'http://localhost:8080/ProyectoVaquerasIPC2/RegistroServlet';

  constructor(private http: HttpClient) {}

  login(usuario: Partial<Usuario>): Observable<Usuario> {
    return this.http.post<Usuario>(this.url, usuario);
  }

  obtenerUsuario(correo_usuario: string): Observable<Usuario> {
  return this.http.get<Usuario>(this.url, {
    params: {
      correo_usuario: correo_usuario || ''
    }
  });
}

  registrar(usuario: Partial<Usuario>): Observable<Usuario> {
    return this.http.post<Usuario>(this.url2, usuario);
  }
}