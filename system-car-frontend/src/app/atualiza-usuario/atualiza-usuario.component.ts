import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UserService } from '../service/user.service';
import { ToastrService } from 'ngx-toastr';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-atualiza-usuario',
  templateUrl: './atualiza-usuario.component.html',
  styleUrl: './atualiza-usuario.component.scss'
})
export class AtualizaUsuarioComponent {
  constructor(private userService: UserService, 
    private toastr: ToastrService,
    private datePipe: DatePipe,
    private router: Router,
    public dialogRef: MatDialogRef<AtualizaUsuarioComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  hide = true;

  onCancel(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    console.log(this.data);

    const user = {
      "firstName":this.data.firstName,
      "lastName":this.data.lastName,
      "email":this.data.email,
      "birthday":this.data.birthday,
      "login":this.data.login,
      "password":this.data.password,
      "phone":this.data.phone
    }

    console.log(user);

    this.userService.userUpdate(this.data.id, user).subscribe(data => {
      this.toastr.success('Atualizacao cadastra foi um sucesso.');
      this.dialogRef.close();
    }, error => {
      this.toastr.error('Erro na atualização cadastral', error.error);
    });
  }
}