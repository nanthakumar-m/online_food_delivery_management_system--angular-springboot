import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NavbarComponent } from '../../../components/navbar/navbar.component';
import { NavbarService } from '../../../components/navbar/navbar.service';

@Component({
  selector: 'app-restaurant-dashboard',
  standalone: true,
  templateUrl: './restaurant-dashboard.component.html',
  styleUrls: ['./restaurant-dashboard.component.css'],
  imports:[NavbarComponent]
})
export class RestaurantDashboardComponent {
  restaurantId: string | null = null;

  constructor(private router: Router,private navBarService:NavbarService) {}

  ngOnInit(): void {
    // Retrieve the restaurantId from localStorage and decode it
    const restaurantIdFromLocal = localStorage.getItem('restaurantId');
    if (restaurantIdFromLocal) {
      this.restaurantId = restaurantIdFromLocal; // Decode the restaurantId
    } else {
      // If no restaurantId is found, redirect to login
      this.router.navigate(['/login']);
    }
    
  }

  // Navigate to specific routes
  navigateTo(route: string): void {
    if (this.restaurantId) {
      if (route === 'view-menu') {
        this.router.navigate([`/menu/${this.restaurantId}`]);

      } else if (route === 'orders') {
        this.router.navigate([`/orders/${this.restaurantId}`]);
      }
      else if(route==='Add-Menu')
      {
        this.router.navigate([`manage-menu/${this.restaurantId}`]);
      }
    } else {
      console.error('Restaurant ID not found in localStorage');
    }
  }

  // Logout and redirect to landing page
  logout(): void {
    this.navBarService.logout();
  }
}
