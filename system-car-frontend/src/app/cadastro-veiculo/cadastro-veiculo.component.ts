import { Component } from '@angular/core';
import { UserService } from '../service/user.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-cadastro-veiculo',
  templateUrl: './cadastro-veiculo.component.html',
  styleUrl: './cadastro-veiculo.component.scss'
})
export class CadastroVeiculoComponent {
  carInput = { year: null, licensePlate: '', model: '', color: ''};
  constructor(private userService: UserService, private toastr: ToastrService) {}

  register() {
    this.userService.registerCar(this.carInput).subscribe(
      response => {
        console.log(response);
        this.clearForm();
        this.toastr.success('Veículo cadastrado com sucesso', 'Sucesso');
      },
      error => {
        this.toastr.error(`Erro ao cadastrar veículo: ${error.message}`, 'Erro');
      }
    );
  }

  clearForm() {
    this.carInput = { year: null, licensePlate: '', model: '', color: '' };
  }
}