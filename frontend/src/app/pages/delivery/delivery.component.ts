import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-delivery',
  standalone: false,
  templateUrl: './delivery.component.html',
  styleUrls: ['./delivery.component.css'],
})
export class DeliveryComponent implements OnInit, OnDestroy {
  orderId!: string;
  deliveryStatus: string = 'Accepted'; // Default status
  statuses: string[] = [
    'Accepted',
    'Assigning Agent',
    'Out for Delivery',
    'Delivers in 5 mins',
    'Delivered',
  ];
  private intervalId: any; // To store the interval reference

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    // Get the orderId from the route parameters
    this.orderId = this.route.snapshot.paramMap.get('orderId')!;
    this.startFetchingDeliveryStatus();
  }

  startFetchingDeliveryStatus(): void {
    // Fetch delivery status from the backend every 3 seconds
    this.intervalId = setInterval(() => {
      this.fetchDeliveryStatus();
      console.log("calling");
    }, 3000); // Adjust the interval as needed
  }

  fetchDeliveryStatus(): void {
    // Use the base URL from the environment file
    const apiUrl = `${environment.apiUrl}/customer/track-delivery/${this.orderId}`;

    this.http.get<{ status: string }>(apiUrl).subscribe({
      next: (response) => {
        if (this.statuses.includes(response.status)) {
          this.deliveryStatus = response.status; // Update the delivery status
        } else {
          console.error('Invalid status received from backend:', response.status);
        }
      },
      error: (err) => {
        console.error('Error fetching delivery status:', err);
      },
    });
  }

  getStatusIndex(): number {
    return this.statuses.indexOf(this.deliveryStatus);
  }

  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }
}