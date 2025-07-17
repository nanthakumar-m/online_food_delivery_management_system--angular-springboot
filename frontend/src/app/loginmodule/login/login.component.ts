import { HttpClient, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  Validators,
} from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { User } from '../model/user';
import { LoginService } from '../user-management/login.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup = new FormGroup({});

  // injecting the formBuilder and httpClient modules
  constructor(
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private loginService: LoginService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    // initialzing the formgroup on component render
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      role: ['customer', Validators.required],
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      let user: User = {
        email: this.loginForm.value.email,
        password: this.loginForm.value.password,
      };

      let jwtToken = '';

      // calling the authentication function according to the role selected
      // if the role is admin then calling the admin login api

      if (this.loginForm.value.role == 'customer') {
        this.loginService.customerLogin(user).subscribe({
          next: (response) => {
            jwtToken = response.jwtToken;
            let customerId = response.userId;

            localStorage.setItem('jwtToken', jwtToken);
            localStorage.setItem('customerId', customerId);
            localStorage.setItem("role","customer")


            this.snackBar.open('Login succesfull...!', '', {
              duration: 1000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
              panelClass: ['custom-snackbar'],
            });
            setTimeout(() => this.router.navigate(['']), 2000);
          },
          error: (err) => {
            // when invalid email or password is entered or  when other than customer tries to login through customer login
            if (err.status == 401 || err.status == 404) {
              this.snackBar.open(err.error, '', {
                duration: 4000,
                verticalPosition: 'top',
                horizontalPosition: 'center',
                panelClass: ['custom-snackbar-error'],
              });
            }
          },
        });
      }

      // if the role is admin then calling the admin login api
      else if (this.loginForm.value.role == 'admin') {
        this.loginService.adminLogin(user).subscribe({
          next: (token) => {
            jwtToken = token;
            localStorage.setItem('jwtToken', jwtToken);
            localStorage.setItem("role","admin")

            this.snackBar.open('Login succesfull...!', '', {
              duration: 1000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
              panelClass: ['custom-snackbar'],
            });

            setTimeout(() => this.router.navigate(['/AdminDashboard']), 2000);


          },
          error: (err) => {
            console.log(err.message);
            // when invalid email or password is entered or  when other than customer tries to login through customer login
            if (err.status == 401 || err.status == 404) {
              let errorMessage = err.error;
              this.snackBar.open(errorMessage, '', {
                duration: 4000,
                verticalPosition: 'top',
                horizontalPosition: 'center',
                panelClass: ['custom-snackbar-error'],
              });
            }
          },
        });
      }

      // if the role is restaurant then calling the restaurant login api
      else if (this.loginForm.value.role == 'restaurant') {
        this.loginService.restaurantLogin(user).subscribe({
          next: (response) => {
            jwtToken = response.jwtToken;
            let restaurantId = response.userId;
            localStorage.setItem('jwtToken', jwtToken);
            localStorage.setItem('restaurantId', restaurantId);

            localStorage.setItem("role","restaurant")
            this.snackBar.open('Login succesfull...!', '', {
              duration: 1000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
              panelClass: ['custom-snackbar'],
            });
            setTimeout(() => this.router.navigate(['/RestaurantDashboard']), 2000);
            


          },
          error: (err) => {
            // when invalid email or password is entered or  when other than customer tries to login through customer login
            if (err.status == 401 || err.status == 404) {
              this.snackBar.open(err.error, '', {
                duration: 4000,
                verticalPosition: 'top',
                horizontalPosition: 'center',
                panelClass: ['custom-snackbar-error'],
              });
            }
          },
        });
      }
    } else {
      console.log('something missing');
    }
  }

  sampleSubmit() {
    let responseMessage = '';

    this.loginService.sampleSubmit().subscribe((msg) => {
      responseMessage = msg;
      console.log(responseMessage);
    });
  }
}
