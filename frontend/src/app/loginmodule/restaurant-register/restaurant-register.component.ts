import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../user-management/login.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Restaurant } from '../model/restaurant';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-restaurant-register',
  standalone: false,
  templateUrl: './restaurant-register.component.html',
  styleUrl: './restaurant-register.component.css',
})
export class RestaurantRegisterComponent implements OnInit {
  formMode: 'register' | 'update' = 'register';

  id: number = 0;

  restaurantRegisterForm: FormGroup = new FormGroup({});
  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private activatedRoute: ActivatedRoute,
    private snackBar: MatSnackBar,
    private router:Router
  ) {}

  ngOnInit(): void {
    this.restaurantRegisterForm = this.formBuilder.group({
      restaurantName: ['', Validators.required],
      restaurantEmail: ['', [Validators.required, Validators.email]],
      restaurantPassword: ['', [Validators.required, Validators.minLength(6)]],
      location: ['', Validators.required],
    });

    // checking if the id is present in the url if yes then we are in update mode
    this.id = Number(this.activatedRoute.snapshot.paramMap.get('id'));

    if (this.id) {
      this.formMode = 'update';

      let restaurant: Restaurant;

      // loading the restaurant data for update
      this.loginService.getRestaurantById(this.id).subscribe((data) => {
        restaurant = data;
        this.restaurantRegisterForm.patchValue(restaurant);
      });
    }
  }

  onSubmit() {
    console.log(this.restaurantRegisterForm.value);

    if (this.restaurantRegisterForm.valid) {
      let restaurant = this.restaurantRegisterForm.value;

      let responseMessage = '';

      // if id is present then we are in update mode
      if (this.id != 0) {
        this.loginService.updateRestaurant(this.id, restaurant).subscribe({
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
        this.loginService.registerRestaurant(restaurant).subscribe({
          next: (msg) => {
            responseMessage = msg;
            this.snackBar.open(responseMessage, '', {
              duration: 2000,
              verticalPosition: 'top',
              horizontalPosition: 'center',
              panelClass: ['custom-snackbar'],

            });

            setTimeout(() => {
              this.router.navigate(['/login']);
            }, 3000);
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
    let restaurantToUpdate = this.restaurantRegisterForm.value;
    this.loginService.changePassword(this.id, restaurantToUpdate);
  }
}
