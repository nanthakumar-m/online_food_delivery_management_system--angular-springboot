import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MenuItem } from '../models/menu-item.model';

@Injectable({
  providedIn: 'root'
})
export class RestaurantMenuItemService {
  
  private apiUrl = 'http://localhost:8085/restaurant/menu_items';
  constructor(private http: HttpClient) { }

  

  getMenuItems(restaurantId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/?restaurantId=${restaurantId}`);
  }
  
  deleteMenuItem(menuId:number):Observable<any>{
    return this.http.delete<any>(`${this.apiUrl}/delete/${menuId}`);
  }

  getMenuItemById(menuId:number):Observable<any>{
    return this.http.get<any>(this.apiUrl+"/searchMenuItemById/"+menuId);
      
  }
  
  updateMenuItem(menuId:number,menuItem:MenuItem){
    
      return this.http.put<any>(this.apiUrl + '/updateMenuItem', menuItem, {
      params: { menuId: menuId } 
});
  
  }  
  
  addMenuItem(restaurantId:number,menuItem:MenuItem):Observable<any>{
          return this.http.post<any>(this.apiUrl+"/addMenuItem/"+restaurantId,menuItem)
      }


  getOrdersForRestaurant(restaurantId:number):Observable<any>{
    return this.http.get<any>(this.apiUrl+"/getOrders/"+restaurantId)
  }
}

  
    

