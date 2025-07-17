import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class RestaurantsService {
  private apiUrl = `${environment.apiUrl}/customer/restaurant`; // Base API URL

  constructor(private http: HttpClient) {}


  getRestaurants() {
    return this.http.get<any>(this.apiUrl);
  }
}
