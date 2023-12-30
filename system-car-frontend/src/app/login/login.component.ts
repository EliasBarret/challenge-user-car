import { Component } from '@angular/core';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginInput = { login: '', password: '' };

  constructor(private userService: UserService, private toastr: ToastrService, private router: Router) {}

  login() {
    this.userService.login(this.loginInput).subscribe(
      response => {
        const accessToken = response.accessToken;
        localStorage.setItem('token', accessToken);
        this.redirectToHome();
      },
      error => {
        if (error.status === 401) {
          this.handleInvalidSession();
        } else {
          this.toastr.error('Erro durante o login', error.error);
          console.log(error.error);
        }
      }
    );
  }
  
  private handleInvalidSession() {
    localStorage.removeItem('token');
    this.toastr.error('Sessão inválida. Faça login novamente.', 'Erro de Autenticação');
    this.router.navigate(['/login']);
  }

  redirectToHome() {
    this.router.navigate(['/home']);
  }
}
