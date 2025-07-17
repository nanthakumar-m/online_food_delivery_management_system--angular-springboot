import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router'; // Import RouterModule
import { AppComponent } from './app.component';
import { FooterComponent } from './components/footer/footer.component';
import { RestaurantsComponent } from './pages/restaurants/restaurants.component';
import { MenuItemsComponent } from './pages/menu-items/menu-items.component';
import { CartComponent } from './pages/cart/cart.component';
import { HomeComponent } from './pages/home/home.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { OrdersComponent } from './pages/orders/orders.component'; 
import { CommonModule } from '@angular/common'; 
import { UserManagementModule } from './loginmodule/user-management/user-management.module';

import {  provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptor } from '../../auth.interceptor';
import { PaymentSuccessComponent } from './components/payment-success/payment-success.component';
import { DeliveryComponent } from './pages/delivery/delivery.component';;


@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    PaymentSuccessComponent,
    DeliveryComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule, 
    NavbarComponent,
    RestaurantsComponent,
    MenuItemsComponent,
    HomeComponent,
    CommonModule,
   
    // login module
    AppRoutingModule,
    UserManagementModule,
  ],
  providers: [ provideHttpClient(withInterceptors([authInterceptor]))
],
  bootstrap: [AppComponent],
})
export class AppModule {}