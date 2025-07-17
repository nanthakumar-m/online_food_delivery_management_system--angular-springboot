import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Order } from '../models/order.model';
import { RestaurantMenuItemService } from '../services/menu-item.service';
import { NgFor } from '@angular/common';
import { NavbarComponent } from '../../../components/navbar/navbar.component';

@Component({
  selector: 'app-orders',
  imports: [NgFor,NavbarComponent],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class RestaurantOrdersComponent implements OnInit {

  constructor(private activatedRoute:ActivatedRoute,private menuItemServcie:RestaurantMenuItemService){}

  restaurantId:number=0
  orders:Order[]=[]

  ngOnInit(): void {
    this.restaurantId = Number(this.activatedRoute.snapshot.paramMap.get('restaurantId'));

    this.menuItemServcie.getOrdersForRestaurant(this.restaurantId).subscribe(
      (response)=>this.orders=response
    )


  }


}
