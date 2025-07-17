import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Restaurant } from '../models/restaurant.model';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AdminRestaurantService {
  private apiUrl = 'http://localhost:8085/admin/restaurant';

  constructor(private http: HttpClient) {}

 
getAllRestaurant(): Observable<Restaurant[]> {
    return this.http.get<{ restaurants: Restaurant[] }>(this.apiUrl).pipe(
    map((response: { restaurants: any; }) => response.restaurants)
    );
    }


    deleteRestaurant(restaurantId:number):Observable<any>{
        return this.http.delete<any>(this.apiUrl+"/deleteRestaurant/"+restaurantId)
    }

    getRestaurant(restaurantId:number):Observable<any>{
        return this.http.get<any>(this.apiUrl+"/searchRestaurantById/"+restaurantId)
    }

    // updateRestaurant(restaurantId:number):Observable<any>{
    //     return this.http.put<any>(this.apiUrl+"/updateRestaurantDetails/"+restaurantId)
    // }
    updateRestaurant(restaurantId:number,restaurant:Restaurant){
        return this.http.put<any>(this.apiUrl+"/updateRestaurantDetails/"+restaurantId,restaurant)
    }

    addRestaurant(restaurant:Restaurant):Observable<any>{
        return this.http.post<any>(this.apiUrl+"/addRestaurant",restaurant)
    }
}