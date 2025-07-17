import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NavbarComponent } from '../../../components/navbar/navbar.component';
import { NavbarService } from '../../../components/navbar/navbar.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css'],
  imports:[NavbarComponent]
})
export class AdminDashboardComponent {
  constructor(private router: Router,private navBarService:NavbarService) {}

  // Navigate to specific routes
  navigateTo(route: string): void {
    this.router.navigate([`/${route}`]);
  }

  // Logout and redirect to login page
  logout(): void {
    this.navBarService.logout();
  }

  showRestaurants():void{
    this.router.navigate(['/show-restaurant']);

  }
}
