import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common'; 
import { NavbarService } from './navbar.service'; 

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  imports: [CommonModule] 
})
export class NavbarComponent {

  customerId: string | null = null; 
  role: string | null = 'customer';


  constructor(private navbarService: NavbarService, private router: Router) {}

  ngOnInit(): void {
    this.customerId = this.navbarService.getcustomerId();  // Retrieve the user's ID from the service
    this.role = this.navbarService.getRole();
  }

  scrollToSection(sectionId: string): void {
    const element = document.getElementById(sectionId);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }

  redirectToHome(): void {
    if (this.role === 'admin') {
      this.router.navigate(['/AdminDashboard']);
    } else if (this.role === 'restaurant') {
      this.router.navigate(['/RestaurantDashboard']);
    } else {
      this.router.navigate(['/']); // Default for users
    }
  }

  redirectToOrders(): void {
    this.navbarService.redirectToOrders(); 
  }

  redirectToCart(): void {
    this.router.navigate(['/cart']); 
  }

  redirectToeditProfile(): void {
   let customerId = this.navbarService.getcustomerId();  // Retrieve the user's ID from the service

   if(this.role=="restaurant"){
    const restaurantId=Number(localStorage.getItem("restaurantId"))
    this.router.navigate(['/edit-restaurant/',restaurantId]); 

   }

    this.router.navigate(['/edit-customer', customerId]); 
    console.log("Redirecting to edit profile for customer ID:", this.customerId);
  }

  // Check user is logged in
  isLoggedIn(): boolean {
    return this.navbarService.isLoggedIn(); 
  }

  // Logout
  logout(): void {
    this.navbarService.logout();
  }
}