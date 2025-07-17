import { Component, OnInit } from '@angular/core';
import { HomeService } from './home.service';
import { Router } from '@angular/router'; // Import Router
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { NavbarComponent } from '../../components/navbar/navbar.component';


@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  imports: [CommonModule, RouterModule, HttpClientModule,NavbarComponent] 
})
export class HomeComponent implements OnInit {
  restaurants: any[] = []; 
  firstEightRestaurants: any[] = []; 

  constructor(private homeService: HomeService, private router: Router) {} 

  ngOnInit(): void {
    // Fetch restaurants 
    this.homeService.getRestaurants().subscribe(
      (data) => {
        console.log('API Response:', data); 

        // Extract the array of restaurants
        if (data && Array.isArray(data.restaurant)) {
          this.restaurants = data.restaurant;
          this.firstEightRestaurants = this.restaurants.slice(0, 8); // Get the first 8 restaurants
        } else {
          console.error('Unexpected API response format:', data);
        }
      },
      (error) => {
        console.error('Error fetching restaurants:', error);
      }
    );
  }

  redirectToMenu(restaurantId: number): void {
    console.log("Redirecting to menu items for restaurantId:", restaurantId); // Debugging log
    this.router.navigate([`/restaurant/menu_items/${restaurantId}`]);
  }

  isLoggedIn(): boolean {
    return this.homeService.isLoggedIn(); 
  }

  // Redirect login page
  redirectToLogin(): void {
    this.router.navigate(['/login']);
  }

  // Redirect register page
  redirectToRegister(): void {
    this.router.navigate(['/register']);
  }
}
