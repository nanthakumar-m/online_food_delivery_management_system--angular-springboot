import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItemsService } from './menu-items.service';
import { NavbarComponent } from '../../components/navbar/navbar.component';

@Component({
  selector: 'app-menu-items',
  standalone: true,
  imports: [CommonModule,NavbarComponent],
  templateUrl: './menu-items.component.html',
  styleUrls: ['./menu-items.component.css'],
})
export class MenuItemsComponent implements OnInit {
  restaurantId!: number;
  menuItems: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private menuItemsService: MenuItemsService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Get the restaurantId
    this.restaurantId = +this.route.snapshot.paramMap.get('restaurantId')!;
    console.log('Restaurant ID:', this.restaurantId);
    this.fetchMenuItems();
  }

  fetchMenuItems(): void {
    this.menuItemsService.getMenuItems(this.restaurantId).subscribe(
      (data) => {
        if (data && data.menuItem) {
          this.menuItems = data.menuItem;
          console.log(this.menuItems);
        } else {
          console.error('Menu Items not found in the response:', data);
        }
      },
      (error) => {
        console.error('Error fetching menu items:', error);
      }
    );
  }

  addToCart(item: any): void {
    this.menuItemsService.addToCart(item);
    localStorage.setItem('currentRestaurant', this.restaurantId.toString());
  }
}
