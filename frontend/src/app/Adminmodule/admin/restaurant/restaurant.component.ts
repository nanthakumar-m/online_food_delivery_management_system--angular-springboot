import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { Restaurant } from '../models/restaurant.model';
import { AdminRestaurantService } from '../services/restaurant.service';
import { HttpClientModule } from '@angular/common/http';
import 'bootstrap/dist/css/bootstrap.min.css';

import { NgxPaginationModule } from 'ngx-pagination';

import { FormsModule } from '@angular/forms'; // ✅ Import FormsModule
import { NavbarComponent } from '../../../components/navbar/navbar.component';


@Component({
  selector: 'app-restaurant',
  standalone: true,
  imports: [CommonModule, HttpClientModule,RouterModule,NgxPaginationModule,FormsModule ,NavbarComponent],
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.css']
})
export class AdminRestaurantsComponent implements OnInit {
  restaurants: Restaurant[] = [];
  

  
searchQuery: string = '';
selectedLocation: string | null = null;
uniqueLocations: string[] = [];
filteredRestaurants: Restaurant[] = [];

  constructor(private restaurantService: AdminRestaurantService, private router: Router) {}

  ngOnInit(): void {
    this.restaurantService.getAllRestaurant().subscribe(data => {
      this.restaurants = data;
      this.filteredRestaurants = data;
      this.uniqueLocations = [...new Set(data.map(r => r.location))];
    });
  }
  

  deleteRestaurant(restaurantId:number){
    this.restaurantService.deleteRestaurant(restaurantId).subscribe({
      next:(response)=>{
        alert(response.message)
       // ✅ Remove the deleted restaurant from the local array
        this.restaurants = this.restaurants.filter(restaurant => restaurant.restaurantId !== restaurantId);
      }
    })
  }
  
  filterRestaurants(): void {
    this.filteredRestaurants = this.restaurants.filter((restaurant) =>
      restaurant.restaurantName
        .toLowerCase()
        .includes(this.searchQuery.toLowerCase())
    );
    if (this.selectedLocation) {
      this.filteredRestaurants = this.filteredRestaurants.filter(
        (restaurant) => restaurant.location === this.selectedLocation
      );
    }
  }
  
  
  filterByLocation(location: string): void {
    this.selectedLocation = location;
    this.filterRestaurants();
  }

  // Clear the location filter
  clearFilter(): void {
    this.selectedLocation = null;
    this.filterRestaurants();
  }
}
