import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'; // Import Router
import { OrdersService } from './orders.service';
import { CommonModule } from '@angular/common'; // Import CommonModule
import { NavbarComponent } from '../../components/navbar/navbar.component';

@Component({
  selector: 'app-orders',
  standalone: true, // Mark as standalone
  imports: [CommonModule,NavbarComponent], // Add CommonModule here
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css'],
})
export class OrdersComponent implements OnInit {
  orders: any[] = []; // Array to store orders
  customerId!: string;

  constructor(
    private ordersService: OrdersService,
    private route: ActivatedRoute,
    private router: Router // Inject Router
  ) {}

  ngOnInit(): void {
    // Get the customerId from the route parameters
    this.customerId = this.route.snapshot.paramMap.get('customerId')!;
    this.fetchOrders();
  }

  fetchOrders(): void {
    this.ordersService.getOrders(this.customerId).subscribe(
      (response) => {
        if (response && Array.isArray(response)) {
          // Sort orders in descending order by orderAcceptedTime
          this.orders = response.sort(
            (a, b) =>
              new Date(b.orderAcceptedTime).getTime() -
              new Date(a.orderAcceptedTime).getTime()
          );
        } else {
          console.error('Unexpected API response format:', response);
        }
      },
      (error) => {
        console.error('Error fetching orders:', error.message);
      }
    );
  }

  redirectToDelivery(orderId: string): void {
    this.router.navigate([`/customer/track-delivery/${orderId}`]); // Use Router for navigation
  }
}
