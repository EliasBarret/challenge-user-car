import { Component } from '@angular/core';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrl: './cadastro.component.scss'
})
export class CadastroComponent {
  userInput = { firstName: '', lastName: '', email: '', birthday: '', login: '', phone: '', password: ''};
  hide = true;
  
  constructor(private userService: UserService, private toastr: ToastrService, private router: Router) {}

  register() {
    this.userService.register(this.userInput).subscribe(
      response => {
        console.log(response);
        this.clearForm();
        this.toastr.success('Usuario Cadastrado com sucesso', 'Sucesso');
      },
      error => {
        this.toastr.error(`Erro ao cadastrar Usuario: ${error.error}`, 'Erro');
      }
    );
  }

  clearForm() {
    this.userInput = { firstName: '', lastName: '', email: '', birthday: '', login: '', phone: '', password: ''};
  }
}