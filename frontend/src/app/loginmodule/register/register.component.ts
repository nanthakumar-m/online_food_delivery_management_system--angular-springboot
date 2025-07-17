import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../user-management/login.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Customer } from '../model/customer';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup = new FormGroup({});

  customer!: Customer;

  formMode: 'register' | 'update' = 'register';

  //  initialzing the id to zero coz to check whether it is update or register
  id: number = 0;

  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private snackBar: MatSnackBar,
    private activatedRoute: ActivatedRoute,
    private router:Router
  ) {}

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      address: ['', Validators.required],
    });

    // changing the form mode if it is update

    this.id = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    if (this.id) {
      this.formMode = 'update';

      // loading the customer data for update
      this.loginService.getCustomerById(this.id).subscribe((data) => {
        this.customer = data;

        this.registerForm.patchValue(this.customer);
      });
    }
  }

  onRegisterSubmit() {
    if (this.registerForm.valid) {
      let customer = this.registerForm.value;

      let responseMessage = '';


      // if id present means update
      if (this.id != 0) {
        console.log(customer);
        let responseMsg = '';
        this.loginService.updateCustomer(this.id, customer).subscribe({
          next: (msg) => {
            responseMessage = msg;
            this.snackBar.open(responseMessage, '', {
              duration: 2000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
              panelClass: ['custom-snackbar'],

            });
            
           
          },
        });
      }

      // no id means register
      else {
        let responseMessage = '';
        this.loginService.registerCustomer(customer).subscribe({
          next: (msg) => {
            responseMessage = msg;
            this.snackBar.open(responseMessage, '', {
              duration: 2000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
              panelClass: ['custom-snackbar'],

            });

            if(responseMessage!="Email already exists"){
              setTimeout(() => {
                this.router.navigate(['/login']);
              }, 3000);
            }
   
          },
          error: (err) => {
            responseMessage = err.error;
            this.snackBar.open(responseMessage, '', {
              duration: 2000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
              panelClass: ['custom-snackbar-error'],

            });
          },
        });
      }
    }
  }

  changePassword() {
    this.loginService.changePassword(this.id, this.customer);
  }
}
