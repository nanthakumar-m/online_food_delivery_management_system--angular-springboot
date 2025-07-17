import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Customer } from '../model/customer';
import { Restaurant } from '../model/restaurant';
import { UserService } from '../user-management/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoginService } from '../user-management/login.service';
import * as bcrypt from 'bcryptjs';

const oldPasswordInput = 'userTypedOldPassword';
const storedHashedPassword = '...'; // This should come from backend (not recommended)

const isMatch = bcrypt.compareSync(oldPasswordInput, storedHashedPassword);

 

@Component({
  selector: 'app-change-password',
  standalone: false,
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.css'
})
export class ChangePasswordComponent implements OnInit{

  updateType:'customer' | 'restaurant'= 'customer'

  user:Customer | Restaurant |null =null
  userId:number=0
  

  changePasswordForm:FormGroup=new FormGroup({});

  constructor(private formBuilder:FormBuilder,
    private userService:UserService,
    private snackBar:MatSnackBar,
    private loginService:LoginService
  ){}

  ngOnInit(): void {
    this.changePasswordForm=this.formBuilder.group({
      email:['',Validators.required],
      oldPassword:['',Validators.required],
      newPassword:['',Validators.required]
    })

    // getting the user data from the user service
    this.user=this.userService.getUser()
    this.userId=this.userService.getUserId()

    console.log(this.user)
    console.log(this.userId)

    // checking if there is user and he is  customer or restaurant
    if(this.user && 'phone' in this.user){
      this.updateType='customer';
      this.changePasswordForm.patchValue({
        email:this.user.email
      })
    }

    else{
      this.updateType='restaurant';
      this.changePasswordForm.patchValue({
        email:this.user?.restaurantEmail
      })
    }
    
  }

  onSubmit(){
    if(this.changePasswordForm.valid){
      
      // password updation for customer

      if (this.updateType=='customer' && this.user && 'phone' in this.user) {

        const oldPasswordInput = this.changePasswordForm.value.oldPassword;
        const storedHashedPassword =this.user?.password; 
        const isMatch = bcrypt.compareSync(oldPasswordInput, storedHashedPassword);


        if(isMatch){
          
          // updating password should not be same as old
          if(this.changePasswordForm.value.newPassword===this.changePasswordForm.value.oldPassword){
            this.snackBar.open("New Password cannot be same as old","",{
              duration:3000,
              verticalPosition:'top',
              horizontalPosition:'center',
              panelClass: ['custom-snackbar-error'],

            
            });
          }
          else{
            this.user.password=this.changePasswordForm.value.newPassword
            // updating the user in the database
            this.loginService.updateCustomer(this.userId,this.user).subscribe({
              next:(data)=>{
                console.log(data)
              }
            })
            this.snackBar.open("Password updated successfully...!","",{
              duration:4000,
              verticalPosition:'top',
              horizontalPosition:'center',
              panelClass: ['custom-snackbar'],

            });
          }
        }
        else{         
          this.snackBar.open("Old password is incorrect...!","",{
            duration:2000,
            verticalPosition:'top',
            horizontalPosition:'center',
            panelClass: ['custom-snackbar-error'],

          });
        }
      }

      // password updation for restaurant
      else if(this.updateType=='restaurant' && this.user && 'restaurantEmail' in this.user){

        const oldPasswordInput = this.changePasswordForm.value.oldPassword;
        const storedHashedPassword =this.user?.restaurantPassword; 
        const isMatch = bcrypt.compareSync(oldPasswordInput, storedHashedPassword);
        


        if (isMatch){

          // updating password should not be same  as old
          if (this.changePasswordForm.value.newPassword===this.changePasswordForm.value.oldPassword){
            this.snackBar.open("New Password cannot be same as old","",{
              duration:4000,
              verticalPosition:'top',
              horizontalPosition:'center',
              panelClass: ['custom-snackbar-error'],

              });
          }else{
            // setting the new password in user object
            this.user.restaurantPassword=this.changePasswordForm.value.newPassword
            this.loginService.updateRestaurant(this.userId,this.user).subscribe({
              next:(msg)=>
              this.snackBar.open("Password updated successfully...!","",{
                duration:4000,
                verticalPosition:'top',
                horizontalPosition:'center',
                panelClass: ['custom-snackbar'],

              })
            })
        
          }
        }
        else{
          this.snackBar.open("Old password is incorrect...!","",{
            duration:4000,
            verticalPosition:'top',
            horizontalPosition:'center',
            panelClass: ['custom-snackbar-error'],
          });
        }

      }
    }
  }


  
  

}
