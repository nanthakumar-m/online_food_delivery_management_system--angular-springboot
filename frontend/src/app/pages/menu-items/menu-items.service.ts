import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class MenuItemsService {
  private apiUrl = `${environment.apiUrl}/customer/restaurant/menu_items`;

  constructor(
    private http: HttpClient,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  // Fetch menu items for a restaurant
  getMenuItems(restaurantId: number) {
    return this.http.get<any>(`${this.apiUrl}/${restaurantId}`);
  }

  // Add an item to the cart
  addToCart(item: any): void {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
      alert('Please Login');
      this.router.navigate(['/login']); // Redirect to login page
      return;
    }

    // Retrieve existing cart items from localStorage
    const cartItems = JSON.parse(localStorage.getItem('cartItem') || '[]');

    // Add to the cart
    cartItems.push(item);

    // Save the updated cart back to localStorage
    localStorage.setItem('cartItem', JSON.stringify(cartItems));

    // alert('Item added to cart!');
    this.snackBar.open('Item added to cart!', '', {
      duration: 4000,
      verticalPosition: 'top',
      horizontalPosition: 'center',
      panelClass: ['custom-snackbar-message'],
    });
    console.log('Cart Items:', cartItems);
  }
}
