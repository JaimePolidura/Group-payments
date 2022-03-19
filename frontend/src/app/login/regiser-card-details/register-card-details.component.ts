import { Component, OnInit } from '@angular/core';
import {StripeElement, StripeElements, StripeElementsOptions} from "@stripe/stripe-js";
import {FormGroup} from "@angular/forms";
import {StripeService} from "ngx-stripe";
import {Authentication} from "../../../backend/authentication/authentication";
import {PaymentsService} from "../../../backend/payments/payments.service";

@Component({
  selector: 'app-regiser-card-details',
  templateUrl: './register-card-details.component.html',
  styleUrls: ['./register-card-details.component.css']
})
export class RegisterCardDetailsComponent implements OnInit {
  card: StripeElement;
  elements: StripeElements;
  stripeForm: FormGroup;

  private clientSecret: string | null | undefined;

  constructor(
    private stripeService: StripeService,
    private auth: Authentication,
    private paymentsService: PaymentsService,
  ){}

  ngOnInit(): void {
    this.setupStripeElements();
    this.setupIntent();
  }

  private async setupIntent() {
    const setupIntent = await this.paymentsService.setupIntent().toPromise();

    if(setupIntent?.error){
      console.log(setupIntent.error);
    }else{
      this.clientSecret = setupIntent?.setupIntent.client_secret;
    }
  }

  registerCarDetails() {
  }

  private setupStripeElements(): void {
    this.stripeService.elements({locale: 'es'}).subscribe(elements => {
      this.elements = elements;
      // Only mount the element the first time
      if (!this.card) {
        this.card = this.elements.create('card', {
          style: {
            base: {
              iconColor: '#666EE8',
              color: '#31325F',
              lineHeight: '40px',
              fontWeight: 300,
              fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
              fontSize: '18px',
              '::placeholder': {
                color: '#CFD7E0'
              }
            }
          }
        });
        this.card.mount('#card-element');
      }
    });
  }
}
