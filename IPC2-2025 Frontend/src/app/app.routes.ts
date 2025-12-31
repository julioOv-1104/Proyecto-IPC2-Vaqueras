import { Routes } from '@angular/router';
import { LoginForm } from './componentes/login-form/login-form';
import { Tienda } from './componentes/tienda/tienda';
import { authGuard } from './guard/auth-guard';
import { loginGuard } from './guard/login-guard';
import { Registro } from './componentes/registro/registro';
import { RegistroComun } from './componentes/registro-comun/registro-comun';
import { RegistroAdmin } from './componentes/registro-admin/registro-admin';
import { VistaAdmin } from './componentes/vista-admin/vista-admin';
import { rolGuard } from './guard/rol-guard';
import { RegistroEmpresario } from './componentes/registro-empresario/registro-empresario';
import { VistaEmpresario } from './componentes/vista-empresario/vista-empresario';
import { Cartera } from './componentes/cartera/cartera';
import { RegistroEmpresa } from './componentes/registro-empresa/registro-empresa';
import { RegistroJuego } from './componentes/registro-juego/registro-juego';
import { AgregarCategoria } from './componentes/agregar-categoria/agregar-categoria';
import { AgregarRecursos } from './componentes/agregar-recursos/agregar-recursos';
import { Biblioteca } from './componentes/biblioteca/biblioteca';

export const routes: Routes = [
    { path: 'login', component: LoginForm, canActivate: [loginGuard] },
    { path: 'tienda', component: Tienda, canActivate: [authGuard], data:{rol: 'USUARIO_COMUN'} },
    { path: 'registro', component: Registro },
    { path: 'biblioteca', component: Biblioteca },
    { path: 'registroComun', component: RegistroComun },
    { path: 'registroAdmin', component: RegistroAdmin },
    { path: 'registroEmpresario', component: RegistroEmpresario },
    { path: 'registroEmpresa', component: RegistroEmpresa },
    { path: 'registroJuego', component: RegistroJuego },
    { path: 'cartera', component: Cartera },
    { path: 'categoria', component: AgregarCategoria },
    { path: 'recursos', component: AgregarRecursos },
    { path: 'empresario', component: VistaEmpresario, canActivate: [authGuard, rolGuard], data: {rol: 'EMPRESARIO'} },
    { path: 'admin', component: VistaAdmin, canActivate: [authGuard, rolGuard], data: {rol: 'ADMIN'}},
    { path: '', pathMatch: 'full', redirectTo: 'login' },

];
