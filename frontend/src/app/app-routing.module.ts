import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RestaurantsComponent } from './pages/restaurants/restaurants.component';
import { MenuItemsComponent } from './pages/menu-items/menu-items.component';
import { CartComponent } from './pages/cart/cart.component';
import { OrdersComponent } from './pages/orders/orders.component';
import { DeliveryComponent } from './pages/delivery/delivery.component';
 
import { LoginComponent } from './loginmodule/login/login.component';
import { RegisterComponent } from './loginmodule/register/register.component';
import { RestaurantRegisterComponent } from './loginmodule/restaurant-register/restaurant-register.component';
import { ChangePasswordComponent } from './loginmodule/change-password/change-password.component';
 
import { AdminDashboardComponent } from './Adminmodule/admin/admin-dashboard/admin-dashboard.component';
import { RestaurantDashboardComponent } from './Adminmodule/restaurant/restaurant-dashboard/restaurant-dashboard.component';
import { RestaurantMenuListComponent } from './Adminmodule/restaurant/menu-list/menu-list.component';
import { RestaurantManageMenuComponent } from './Adminmodule/restaurant/manage-menu/manage-menu.component';
import { RestaurantOrdersComponent } from './Adminmodule/restaurant/orders/orders.component';
import { AdminManageRestaurantComponent } from './Adminmodule/admin/manage-restaurant/manage-restaurant.component';
import { AdminCreateAgentComponent } from './Adminmodule/admin/create-agent/create-agent.component';
import { AdminRestaurantsComponent } from './Adminmodule/admin/restaurant/restaurant.component';
import { PaymentSuccessComponent } from './components/payment-success/payment-success.component';
import { AdminAgentComponent } from './Adminmodule/admin/agent/agent.component';
 
// import {AdminRestaurantsComponent} from "./Adminmodule/admin/restaurant/restaurant.component"
 
export const routes: Routes = [
  // User Module Routes
  { path: '', component: HomeComponent },
  { path: 'restaurants', component: RestaurantsComponent },
  { path: 'restaurant/menu_items/:restaurantId', component: MenuItemsComponent },
  { path: 'cart', component: CartComponent },
  { path: 'orders/customer/:customerId', component: OrdersComponent },
  { path: 'customer/track-delivery/:orderId', component: DeliveryComponent },
 
  // Login Module Routes
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'restaurant-register', component: RestaurantRegisterComponent },
  { path: 'edit-customer/:id', component: RegisterComponent },
  { path: 'edit-restaurant/:id', component: RestaurantRegisterComponent },
  { path: 'restaurant/change-password/:id', component: ChangePasswordComponent },
  { path: 'customer/change-password/:id', component: ChangePasswordComponent },
  { path: 'payment-success', component: PaymentSuccessComponent },
 
  // Admin Module Routes
  { path: 'AdminDashboard', component: AdminDashboardComponent }, // Admin Dashboard
  { path: 'manage-restaurant', component: AdminManageRestaurantComponent },
  { path: 'show-restaurant', component: AdminRestaurantsComponent },
  { path: 'manage-restaurant/:id', component: AdminManageRestaurantComponent },
  { path: 'create-agent', component: AdminCreateAgentComponent },
  { path: 'agent', component: AdminAgentComponent },
 
  // Restaurant Module Routes
  { path: 'RestaurantDashboard', component: RestaurantDashboardComponent },
  {path: 'list', component: AdminRestaurantsComponent },
  { path: 'menu/:id', component: RestaurantMenuListComponent },
  { path: 'manage-menu/:restaurantId', component: RestaurantManageMenuComponent },
  { path: 'manage-menu/:restaurantId/:menuId', component: RestaurantManageMenuComponent },
  { path: 'orders/:restaurantId', component: RestaurantOrdersComponent },
];
 
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
 
 