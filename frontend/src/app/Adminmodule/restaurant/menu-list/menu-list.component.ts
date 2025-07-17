import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component,OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { MenuItem } from '../models/menu-item.model';
import { RestaurantMenuItemService } from '../services/menu-item.service';
import { NavbarComponent } from '../../../components/navbar/navbar.component';

@Component({
  selector: 'app-menu-list',
  standalone:true,
  imports: [CommonModule,HttpClientModule,RouterModule,NavbarComponent],
  templateUrl: './menu-list.component.html',
  styleUrl: './menu-list.component.css'
})
export class RestaurantMenuListComponent implements OnInit {
  menuItems: MenuItem[] =[];
  constructor(private menuItemService: RestaurantMenuItemService,private router:Router,private route: ActivatedRoute ){

  }

  restaurantId=0

  
  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      this.restaurantId = Number(idParam) 
  
      if (this.restaurantId && this.restaurantId > 0) {
        this.menuItemService.getMenuItems(this.restaurantId).subscribe({
          next: data =>{ 
          
        console.log('Menu items received:', data);
        this.menuItems = data.menuItem;},

          error: err => {
            console.error('Error fetching menu items:', err);
          }
        });
      } else {
        console.error('Invalid or missing restaurant ID in route');
      }
    });
  }

  deleteMenuItem(menuId:number){
    this.menuItemService.deleteMenuItem(menuId).subscribe({
      next:(response)=>{
        console.log(response)
        alert(response.message)
        this.menuItems=this.menuItems.filter(item=>item.menuId !==menuId);

      }
    })
  }
  
  




  

  

}
