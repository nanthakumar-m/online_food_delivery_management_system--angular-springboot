import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../../environments/environment'; // Import environment

@Injectable({
  providedIn: 'root',
})
export class OrdersService {
  private baseUrl = `${environment.apiUrl}/customer/orders`; // Use baseUrl from environment

  constructor(private http: HttpClient) {}

  // Fetch orders for a specific customer
  getOrders(customerId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${customerId}`).pipe(
      catchError((error) => {
        console.error('Error fetching orders:', error);
        return throwError(() => new Error('Failed to fetch orders.'));
      })
    );
  }
}
