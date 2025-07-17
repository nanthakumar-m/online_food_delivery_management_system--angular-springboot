import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class NavbarService {
  private customerId: string | null = '123';
  constructor(private router: Router) {}

  // Check if the user is logged in
  isLoggedIn(): boolean {
    return localStorage.getItem('jwtToken') !== null;
  }

  getRole(): string | null {
    return localStorage.getItem('role'); // Role can be 'user', 'restaurant', or 'admin'
  }

  // Redirect to the orders page
  redirectToOrders(): void {
    const customerId = localStorage.getItem('customerId');
    if (customerId) {
      this.router.navigate([`/orders/customer/${customerId}`]);
    } else {
      alert('Please log in to view your orders.');
      this.router.navigate(['/login']);
    }
  }

  getcustomerId(): string | null {
    let customerId=localStorage.getItem("customerId")
    return customerId;
  }

  // Logout the user
  logout(): void {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('customerId');
    localStorage.removeItem("restaurantId")
    localStorage.setItem("role","customer")
    this.router.navigate(['']);
  }
}
