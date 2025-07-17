import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { CartService } from '../../pages/cart/cart.service';

@Component({
  selector: 'app-payment-success',
  standalone: false,
  templateUrl: './payment-success.component.html',
  styleUrl: './payment-success.component.css'
})
export class PaymentSuccessComponent implements OnInit {

  constructor(private route:ActivatedRoute,private cartService:CartService){}
  
  ngOnInit(): void {

      this.route.queryParams.subscribe(params => {
      const paymentIntentId = params['payment_intent_id'];
      console.log("Payment Intent ID:", paymentIntentId);
      // finish the success logic in backend
      this.cartService.updatePayment(paymentIntentId).subscribe(
      {
        next:(msg)=>{
          console.log(msg)
        }
      }
        
      )


      });

  }

 
}
