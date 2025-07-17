import { Component, OnInit } from '@angular/core';
import { RestaurantsService } from './restaurants.service'; // Import RestaurantsService
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Import FormsModule
import { NavbarComponent } from '../../components/navbar/navbar.component';


@Component({
  selector: 'app-restaurants',
  templateUrl: './restaurants.component.html',
  styleUrls: ['./restaurants.component.css'],
  imports: [CommonModule,FormsModule,NavbarComponent] // Import CommonModule and FormsModule for ngIf and ngFor,
})
export class RestaurantsComponent implements OnInit {
  restaurants: any[] = []; // Original list of restaurants
  filteredRestaurants: any[] = []; // Filtered list of restaurants
  searchQuery: string = ''; // Search query for restaurant name
  selectedLocation: string | null = null; // Selected location for filtering
  uniqueLocations: string[] = []; // List of unique locations for the dropdown

  constructor(
    private restaurantsService: RestaurantsService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchRestaurants();
  }

  fetchRestaurants(): void {
    this.restaurantsService.getRestaurants().subscribe(
      (data) => {
        console.log('API Response:', data);
        if (data && Array.isArray(data.restaurant)) {
          this.restaurants = data.restaurant; // Fetch restaurants from API
          this.filteredRestaurants = [...this.restaurants]; // Initialize filtered restaurants
          this.uniqueLocations = Array.from(
            new Set(this.restaurants.map((restaurant) => restaurant.location))
          ); // Extract unique locations
        } else {
          console.error('Unexpected API response format:', data);
        }
      },
      (error) => {
        console.error('Error fetching restaurants:', error);
      }
    );
  }

  // Filter restaurants based on search query
  filterRestaurants(): void {
    this.filteredRestaurants = this.restaurants.filter((restaurant) =>
      restaurant.restaurantName
        .toLowerCase()
        .includes(this.searchQuery.toLowerCase())
    );

    // Apply location filter if selected
    if (this.selectedLocation) {
      this.filteredRestaurants = this.filteredRestaurants.filter(
        (restaurant) => restaurant.location === this.selectedLocation
      );
    }
  }

  // Filter restaurants by location
  filterByLocation(location: string): void {
    this.selectedLocation = location;
    this.filterRestaurants();
  }

  // Clear the location filter
  clearFilter(): void {
    this.selectedLocation = null;
    this.filterRestaurants();
  }

  redirectToMenu(restaurantId: number): void {
    console.log('Redirecting to menu items for restaurantId:', restaurantId); // Debugging log
    this.router.navigate([`/restaurant/menu_items/${restaurantId}`]);
  }
}
