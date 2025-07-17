import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from '../login/login.component';
import { RegisterComponent } from '../register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { RestaurantRegisterComponent } from '../restaurant-register/restaurant-register.component';
import { MatSnackBarModule } from '@angular/material/snack-bar'; 
import { ChangePasswordComponent } from '../change-password/change-password.component';
import { NavbarComponent } from '../../components/navbar/navbar.component';

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    RestaurantRegisterComponent,
    ChangePasswordComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule,
    HttpClientModule,
    MatSnackBarModule, 
    NavbarComponent
  ],
})
export class UserManagementModule {}
