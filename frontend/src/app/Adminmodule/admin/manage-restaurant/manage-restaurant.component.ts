import { NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule,Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Restaurant } from '../models/restaurant.model';
import { AdminRestaurantService } from '../services/restaurant.service';
import 'bootstrap/dist/css/bootstrap.min.css';
import { NavbarComponent } from '../../../components/navbar/navbar.component';

@Component({
  selector: 'app-manage-restaurant',
  imports: [ReactiveFormsModule,NgIf,NavbarComponent],
  templateUrl: './manage-restaurant.component.html',
  styleUrl: './manage-restaurant.component.css'
})
export class AdminManageRestaurantComponent implements OnInit {

  formMode:'register'|'update'='register'

  id:number=0

  restaurantRegisterForm:FormGroup=new FormGroup({});

  constructor(private formBuilder:FormBuilder,private activatedRoute:ActivatedRoute,private restaurantService:AdminRestaurantService){}

  ngOnInit(): void {
    this.restaurantRegisterForm = this.formBuilder.group({
      restaurantName: ['',Validators.required],
      restaurantEmail: ['',[Validators.required,Validators.email]],
      restaurantPassword: ['',[Validators.required]],
      location: ['',Validators.required],
    })
    this.id=Number(this.activatedRoute.snapshot.paramMap.get('id'));
    console.log(this.id)

    if(this.id!=0){
      this.formMode='update'
      let restaurant:Restaurant

      // loading the restaurant data for update 
      this.restaurantService.getRestaurant(this.id).subscribe({
        next:(respone)=>{
          console.log(respone)
          this.restaurantRegisterForm.patchValue(respone)
        }

      })
    }
  }

  onSubmit(){

    if(this.restaurantRegisterForm.valid){
      console.log("hiii")
      let restaurant:Restaurant=this.restaurantRegisterForm.value;

      if(this.formMode=='update'){

        let responseMessage=''
        console.log(restaurant)

        this.restaurantService.updateRestaurant(this.id,restaurant).subscribe({
          next:(response)=>{
            responseMessage=response.message
            alert(responseMessage)
          }
          
        })

      }
      else if (this.formMode=='register'){
        
        let responseMessage=""
        console.log(restaurant);
        this.restaurantService.addRestaurant(restaurant).subscribe({
            next:(response)=>{
              responseMessage=response.message
              alert(responseMessage)
            }
        })

      }
    }

  }

  


}
