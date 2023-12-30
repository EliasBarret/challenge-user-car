import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { CadastroComponent } from './cadastro/cadastro.component';
import { CadastroVeiculoComponent } from './cadastro-veiculo/cadastro-veiculo.component';
import { HomeComponent } from './home/home.component';

import { AuthGuard } from './auth.guard';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    data: { title: 'login' }
  },
  {
    path: 'cadastro',
    component: CadastroComponent,
    data: { title: 'Cadastro Usuario' }
  },
  {
    path: 'cadastroVeiculo',
    component: CadastroVeiculoComponent,
    canActivate: [AuthGuard],
    data: { title: 'Cadastro Veiculo' }
  },
  {
    path: 'home',
    component: HomeComponent, 
    canActivate: [AuthGuard], 
    data: { title: 'Home' }
  },
  { path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
