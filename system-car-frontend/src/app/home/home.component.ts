import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { UserService } from '../service/user.service';
import { CarService } from '../service/car.service';
import { DatePipe } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';


import { Car } from '../../model/car.model';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

import { AtualizaUsuarioComponent } from '../atualiza-usuario/atualiza-usuario.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  displayedColumns: string[] = ['model', 'year', 'licensePlate', 'color'];
  userData: any;
  dataSource: MatTableDataSource<Car> | undefined;
  listCars: Car[] = [];
  isEditing: boolean[] = [];

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private userService: UserService, private carService: CarService, private dialog: MatDialog, private toastr: ToastrService, private router: Router, private datePipe: DatePipe) {}

  ngOnInit() {
    this.loadDataUser();
  }

  get tableDataSource(): MatTableDataSource<Car> {
    return this.dataSource as MatTableDataSource<Car>;
  }

  logout(){
    console.log("saindo");
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
  
  private handleInvalidSession() {
    localStorage.removeItem('token');
    this.toastr.error('Sessão inválida. Faça login novamente.', 'Erro de Autenticação');
    this.router.navigate(['/login']);
 }

  redirectToHome() {
    this.router.navigate(['/home']);
  }

  editCar(index: number) {
    this.isEditing[index] = true;
  }

  cancelEdit(index: number) {
    this.isEditing[index] = false;
    this.loadDataUser();
  }

  deleteCar(carId: number) {
    console.log('Excluir veículo com ID:', carId);

    this.carService.deleteCar(carId).subscribe(data => {
      this.toastr.success('Excluído com sucesso.');
      this.loadDataUser();
    }, error => {
      this.toastr.error('Erro ao excluir veículo', error.error);
    });
  }

  saveCar(index: number, car: Car) {
    this.isEditing[index] = false;
    console.log(car);

    this.carService.updateCar(car.id, car).subscribe(data => {
        this.toastr.success('Veículo editado com sucesso.');
        this.loadDataUser();
    }, error => {
      this.toastr.error('Erro ao editar veiculo', error.error);
    });
  }

  openEditModal() {
    const dialogRef = this.dialog.open(AtualizaUsuarioComponent, {
      width: '400px',
      data: { ...this.userData },
    });
  
    dialogRef.afterClosed().subscribe(result => {
      this.loadDataUser();
      console.log('O modal foi fechado', result);
    });
  }

  private loadDataUser() {
    this.userService.getUserData().subscribe(data => {
      this.userData = data;

      if (this.userData.birthday) {
        this.userData.birthday = this.datePipe.transform(this.userData.birthday, 'dd/MM/yyyy');
      }

      if (this.userData.createdAt) {
        this.userData.createdAt = this.datePipe.transform(this.userData.createdAt, 'dd/MM/yyyy HH:mm:ss');
      }

      if (this.userData.lastLogin) {
        this.userData.lastLogin = this.datePipe.transform(this.userData.lastLogin, 'dd/MM/yyyy HH:mm:ss');
      }

      this.listCars = data.cars;
      this.isEditing = new Array(this.listCars.length).fill(false);
    }, error => {
      if (error.status === 401) {
        console.log(error.status);
        this.handleInvalidSession();
      } else {
        this.toastr.error('Erro ao carregar os itens na tela', error);
      }
    });
  }
}