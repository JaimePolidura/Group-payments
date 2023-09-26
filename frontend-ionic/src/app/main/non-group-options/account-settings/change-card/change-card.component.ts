import {AfterViewInit, Component, OnInit} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {StripeService} from "ngx-stripe";
import {ConfirmCardSetupData, StripeElement, StripeElements} from "@stripe/stripe-js";
import {PaymentsApiService} from "../../../../../shared/payments/payments-api.service";
import {Router} from "@angular/router";
import {AuthenticationCacheService} from "../../../../../shared/auth/authentication-cache.service";
import {ChangeCardRequest} from "../../../../../shared/payments/api/request/change-card-request";
import {ProgressBarService} from "../../../../_shared/loading-progress-bar/progress-bar.service";

@Component({
  selector: 'app-change-card',
  templateUrl: './change-card.component.html',
  styleUrls: ['./change-card.component.scss'],
})
export class ChangeCardComponent implements OnInit, AfterViewInit {
  private card: StripeElement;
  private elements: StripeElements;
  private clientSecret: string | null | undefined;

  public reqToBackendToChangeCard: ChangeCardRequest;
  public reqToBackednToChangeCardSended: boolean = false;

  constructor(public modalService: NgbModal,
              public stripeService: StripeService,
              private paymentsService: PaymentsApiService,
              private router: Router,
              private authCache: AuthenticationCacheService,
              private progressBar: ProgressBarService,
  ) {
    this.reqToBackednToChangeCardSended = false;
  }

  ngAfterViewInit(): void {
    this.setupStripeElements();
    this.setupIntent();
  }

  ngOnInit() {}

  public closeModal(): void {
    this.modalService.dismissAll();
  }

  private setupStripeElements(): void {
    this.stripeService.elements({locale: 'es'}).subscribe(elements => {
      this.elements = elements;

      if (!this.card) {
        this.card = this.elements.create('card');
        this.card.mount('#card-element');
      }
    });
  }

  private async setupIntent() {
    const setupIntent = await this.paymentsService.setupIntent().toPromise();

    if(!setupIntent?.error){
      // @ts-ignore
      this.clientSecret = setupIntent.client_secret;
    }else{
      window.alert("Some error happened try later");
      await this.router.navigate(["/"]);
    }
  }

  public async tryRegisterSetupCard(){
    try{
      await this.setupChangeCard();
      this.progressBar.stop();
      console.log("adios");
    }catch (error) {
      this.progressBar.stop();
    }finally {
      console.log("Hola");
    }
  }

  public async setupChangeCard() {
    this.progressBar.start();

    const confirmCardResult = await this.stripeService.confirmCardSetup(this.clientSecret, this.buildConfirmCardSetupRequest())
      .toPromise();
    const confirmCardResultNull = confirmCardResult == undefined || !confirmCardResult.setupIntent
      || confirmCardResult.setupIntent.payment_method == null || !confirmCardResult.setupIntent.payment_method;

    if(confirmCardResultNull){
      this.progressBar.stop();
      throw new Error('Invalid card details');
    }

    // @ts-ignore
    const paymentMethodRegister: string = confirmCardResult.setupIntent.payment_method;

    const requestToChangeCard: ChangeCardRequest = {
      paymentMethod: paymentMethodRegister,
      dob: { year: 2002, month: 12, day: 12}
    };

    this.paymentsService.prepareChangeCard(requestToChangeCard).subscribe(res => {
      this.reqToBackendToChangeCard = requestToChangeCard;
      this.reqToBackednToChangeCardSended = true;
      this.progressBar.stop();
    }, err => {
      this.progressBar.stop();
    });
  }

  private buildConfirmCardSetupRequest(): ConfirmCardSetupData{
    return { payment_method: {
        // @ts-ignore
        card: this.card,
        billing_details: {
          name: this.authCache.getUsername()
        },
      }};
  }

  public resnedEmail(): void {
    this.paymentsService.prepareChangeCard(this.reqToBackendToChangeCard).subscribe(res => {});
  }
}
