import { Component, OnInit } from '@angular/core';
import {ConfirmCardSetupData, StripeElement, StripeElements, StripeElementsOptions} from "@stripe/stripe-js";
import {FormGroup} from "@angular/forms";
import {StripeService} from "ngx-stripe";
import {Authentication} from "../../../backend/authentication/authentication";
import {PaymentsService} from "../../../backend/payments/payments.service";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpLoadingService} from "../../../backend/http-loading.service";

@Component({
  selector: 'app-regiser-card-details',
  templateUrl: './register-card-details.component.html',
  styleUrls: ['./register-card-details.component.css']
})
export class RegisterCardDetailsComponent implements OnInit {
  card: StripeElement;
  elements: StripeElements;

  private clientSecret: string | null | undefined;
  public isHttpRequestLoading: boolean = false;

  constructor(
    private stripeService: StripeService,
    private auth: Authentication,
    private paymentsService: PaymentsService,
    private router: Router,
    private activeRoute: ActivatedRoute,
    private httpLoader: HttpLoadingService,
  ){}

  ngOnInit(): void {
    this.setupStripeElements();
    this.setupIntent();

    const state: string = this.activeRoute.snapshot.params["state"];
    if(state == 'done')
      this.router.navigate(["/main"]);
  }

  private async setupIntent() {
    const setupIntent = await this.paymentsService.setupIntent().toPromise();

    if(setupIntent?.error){
      console.log(setupIntent.error);
    }else{
      // @ts-ignore
      this.clientSecret = setupIntent.client_secret;
    }
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

  async registerCarDetails() {
    this.isHttpRequestLoading = true;
    this.httpLoader.isLoading.next(true);

    // @ts-ignore
    const result = await this.stripeService.confirmCardSetup(this.clientSecret,  this.buildConfirmCardSetupRequest())
      .toPromise();

    if(result != undefined && result.setupIntent && result.setupIntent.payment_method != null){
      this.paymentsService.createCustomer({paymentMethod: result.setupIntent.payment_method}).subscribe(() => {
        this.paymentsService.createConnectedAccount().subscribe(res => {
          this.isHttpRequestLoading = false;
          this.httpLoader.isLoading.next(false);

          this.goToLink(res.accountLink);
        })
      });
    }
  }

  private buildConfirmCardSetupRequest(): ConfirmCardSetupData{
    return { payment_method: {
        // @ts-ignore
        card: this.card,
          billing_details: {
            name: this.auth.getName()
          },
      }};
  }

  public goToLink(url: string){
    window.open(url, "_self");
  }
}
