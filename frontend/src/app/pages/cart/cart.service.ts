import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private cartItems: any[] = [];
  private baseUrl = environment.apiUrl;
  

  constructor(private http:HttpClient) {
    this.loadCartItems(); // Load cart items on service initialization
  }

  // Load cart items from localStorage
  loadCartItems(): void {
    const storedCart = localStorage.getItem('cartItem');
    this.cartItems = storedCart ? JSON.parse(storedCart) : [];
    this.cartItems.forEach(item => {
      if (!item.quantity) {
        item.quantity = 1;
      }
    });
  }

  // Get all cart items
  getCartItems(): any[] {
    return this.cartItems;
  }

  // Increase the quantity of an item
  increaseQuantity(index: number): void {
    this.cartItems[index].quantity++;
    this.updateCartInLocalStorage();
  }

  // Decrease the quantity of an item
  decreaseQuantity(index: number): void {
    if (this.cartItems[index].quantity > 1) {
      this.cartItems[index].quantity--;
      this.updateCartInLocalStorage();
    }
  }

  // Delete an item from the cart
  deleteItem(index: number): void {
    this.cartItems.splice(index, 1);
    this.updateCartInLocalStorage();
  }

  // Update the cart in localStorage
  updateCartInLocalStorage(): void {
    localStorage.setItem('cartItem', JSON.stringify(this.cartItems));
  }

  // Calculate the total price of the cart
  calculateTotalPrice(): number {
    return this.cartItems.reduce((total, item) => total + (item.quantity * item.price), 0);
  }

  // Checkout the cart
  checkoutCart():Observable<number>{
    let totalAmount=this.calculateTotalPrice()
    let customerId=localStorage.getItem("customerId")
    let restaurantId=localStorage.getItem("currentRestaurant")

    return  this.http.post<number>(`${this.baseUrl}/customer/createOrder/${totalAmount}/${restaurantId}/${customerId}`,'')


    // alert('Thanks For Visiting...🙂');
    // localStorage.removeItem('cartItem');
    // this.cartItems = [];

  
  }

  initiatePayment(orderId:number):Observable<string>{
    return this.http.get(this.baseUrl+"/customer/payments/pay/"+orderId,{responseType:'text'})
  }

  updatePayment(payment_intent_id:string):Observable<string>{
    // once if the payment success clear the cart items
    localStorage.removeItem("cartItem")
    this.cartItems=[]
    return this.http.get(this.baseUrl+"/customer/payments/success",{
      params: { payment_intent_id: payment_intent_id },
      responseType: 'text'
    })
  }

  
}
