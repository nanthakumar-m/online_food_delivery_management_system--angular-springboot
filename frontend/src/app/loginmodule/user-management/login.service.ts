import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Customer } from '../model/customer';
import { Observable } from 'rxjs';
import { Restaurant } from '../model/restaurant';
import { User } from '../model/user';
import { Router } from '@angular/router';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  apiUrl="http://localhost:8085"

  constructor(
    private http:HttpClient ,
    private router:Router ,// getting the instance of Router for navigating
    private userService:UserService
  ) { }

  // ================ Register services =================

  // customer registration
  registerCustomer(customer:Customer):Observable<string>{

     return this.http.post(this.apiUrl+"/customer/register",customer,{responseType:'text'})
  }

  // restaurant registration
  registerRestaurant(restaurant:Restaurant):Observable<string>{

    return this.http.post(this.apiUrl+"/restaurant/register",restaurant,{responseType:'text'})
  }


  // ================ Updation services =================

    // get customer for updation
  getCustomerById(id:number):Observable<Customer>{
    return this.http.get<Customer>(this.apiUrl+"/customer/getCustomer/"+id)
  }

  getRestaurantById(id:number):Observable<Restaurant>{
    return this.http.get<Restaurant>(this.apiUrl+"/restaurant/getRestaurant/"+id,)
  }

  // update customer
  updateCustomer(id:number,customer:Customer):Observable<string>{
    return this.http.put(this.apiUrl+"/customer/update/"+id,customer,{responseType:'text'})
  }

  // update restaurant
  updateRestaurant(id:number,restaurant:Restaurant):Observable<string>{
    return this.http.put(this.apiUrl+"/restaurant/update/"+id,restaurant,{responseType:'text'})
  }

  // same method for both restaurant and customer password updation
  changePassword(id:number,user:Customer | Restaurant){
 
    // if the user containe the phone then it is customer coz restaurant does not have phone
    if('phone' in user){
      // setting the data in userService to access in the change password component
      this.userService.setUser(user,id)
      this.router.navigate([`/customer/change-password/${id}`])
    }
    else{
      this.userService.setUser(user,id)
      this.router.navigate([`/restaurant/change-password/${id}`])

    }

  }




  //================ Login services =================

  //  calling the custmer login api to validate the user (customer)
  customerLogin(user:User):Observable<any>{
    return this.http.post<any>(this.apiUrl+"/customer/login",user)
  }

  //  calling the admin login api to validate the user (admin)
  adminLogin(user:User):Observable<string>{
    return this.http.post(this.apiUrl+"/admin/login",user,{responseType:'text'})

  }


  //  calling the restaurant login api to validate the user (restaurant) 
  restaurantLogin(user:User):Observable<any>{
    return this.http.post<any>(this.apiUrl+"/restaurant/login",user)
  }

  sampleSubmit():Observable<string>{
    return this.http.get(this.apiUrl+"/customer/tokenTest",{responseType:'text'})
  }


}
