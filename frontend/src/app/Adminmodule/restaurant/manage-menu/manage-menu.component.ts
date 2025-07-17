import { NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RestaurantMenuItemService } from '../services/menu-item.service';
import { MenuItem } from '../models/menu-item.model';
import { NavbarComponent } from '../../../components/navbar/navbar.component';

@Component({
  selector: 'app-manage-menu',
  imports: [ReactiveFormsModule,NgIf,NavbarComponent],
  templateUrl: './manage-menu.component.html',
  styleUrl: './manage-menu.component.css'
})
export class RestaurantManageMenuComponent implements OnInit {
  formMode:'create' |'update' ='create'
  menuId:number=0
  restaurantId:number=0

  menuCreateForm:FormGroup=new FormGroup({});

  constructor(private formBuilder:FormBuilder,private activatedRoute:ActivatedRoute,private  menuItemService:RestaurantMenuItemService,private router:Router){}

  ngOnInit(): void {
    this.menuCreateForm = this.formBuilder.group({
      itemName: ['',Validators.required],
      description: ['',[Validators.required]],
      price: ['',[Validators.required]],
    })
    this.restaurantId = Number(this.activatedRoute.snapshot.paramMap.get('restaurantId'));
    this.menuId = Number(this.activatedRoute.snapshot.paramMap.get('menuId'));
    
    if(this.menuId!=0){
      this.formMode='update'
      let menuItem:MenuItem

      // loading the restaurant data for update 
      this.menuItemService.getMenuItemById(this.menuId).subscribe({
        next:(response)=>{
          console.log(response)
          this.menuCreateForm.patchValue(response.item)
          
        }
      })
    }
  }

  onSubmit(){

    if(this.menuCreateForm.valid){
      let menuItem:MenuItem=this.menuCreateForm.value;

      if(this.formMode=='update'){

        let responseMessage=''
        console.log(menuItem)

        this.menuItemService.updateMenuItem(this.menuId,menuItem).subscribe({
          next:(response)=>{
            responseMessage=response.message
            alert(responseMessage)

          }
          
        })

      }
      else if (this.formMode=='create'){
        
        let responseMessage=""
        console.log(menuItem);
        this.menuItemService.addMenuItem(this.restaurantId,menuItem).subscribe({
            next:(response)=>{
              console.log(response)
              responseMessage=response.message
              alert(responseMessage)
            }
        })

      }
    }

  }

  

}



