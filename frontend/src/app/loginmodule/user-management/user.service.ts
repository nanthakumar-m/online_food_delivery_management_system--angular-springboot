import { Injectable } from '@angular/core';
import { Customer } from '../model/customer';
import { Restaurant } from '../model/restaurant';

// this is the service just used to share the data from (customer or restaurant) update component to  change password component

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private user:Customer|Restaurant|null =null;
  private userId:number=0

  setUser(user:Customer|Restaurant, id:number){
    this.userId=id
    this.user=user;
  }

  getUser():Customer|Restaurant|null{
    return this.user;
  }


  getUserId():number{
    return this.userId;
  }
  

}
