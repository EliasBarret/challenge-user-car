// app.module.ts
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule} from '@angular/material/button';
import { MatDividerModule} from '@angular/material/divider';
import { MatDatepickerModule} from '@angular/material/datepicker';
import { MatMenuModule} from '@angular/material/menu';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule} from '@angular/material/form-field';
import { MatNativeDateModule} from '@angular/material/core';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from "@angular/flex-layout";

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login/login.component';
import { CadastroComponent } from './cadastro/cadastro.component';
import { CadastroVeiculoComponent } from './cadastro-veiculo/cadastro-veiculo.component';
import { HomeComponent } from './home/home.component';
import { AtualizaUsuarioComponent } from './atualiza-usuario/atualiza-usuario.component';

@NgModule({
  providers: [DatePipe],
  declarations: [
    AppComponent,
    LoginComponent,
    CadastroComponent,
    HomeComponent,
    CadastroVeiculoComponent,
    AtualizaUsuarioComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatCardModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatDividerModule,
    MatDatepickerModule,
    MatMenuModule,
    MatInputModule,
    MatFormFieldModule,
    MatNativeDateModule,
    ToastrModule.forRoot(),
    FlexLayoutModule,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}