import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { CartService } from './cart.service'; 
import { NavbarComponent } from '../../components/navbar/navbar.component';

@Component({
  selector: 'app-cart',
  standalone: true, 
  imports: [CommonModule,NavbarComponent], 
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  cartItems: any[] = []; // Store cart items

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    
    this.cartService.loadCartItems()
    this.cartItems = this.cartService.getCartItems(); // Load cart items 
  }

  // Increase the quantity 
  increaseQuantity(index: number): void {
    this.cartService.increaseQuantity(index); 
  }

  // Decrease the quantity 
  decreaseQuantity(index: number): void {
    this.cartService.decreaseQuantity(index); 
  }

  // Delete an item from the cart
  deleteItem(index: number): void {
    this.cartService.deleteItem(index); 
    this.cartItems = this.cartService.getCartItems(); 
  }

  // Calculate the total price of the cart
  calculateTotalPrice(): number {
    return this.cartService.calculateTotalPrice();
  }

  // Checkout the cart
  checkoutCart(): void {
    this.cartService.checkoutCart().subscribe({
      next:(orderId)=>{
        console.log(orderId)
        console.log("order placed")

        this.cartService.initiatePayment(orderId).subscribe({
          next:(paymentUrl)=>{
            console.log(paymentUrl)
            // redirect to the payment Url
            window.location.href = paymentUrl;

          }
        })

      }
    }) 
    
  }
}