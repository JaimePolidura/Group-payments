import {Component, OnInit} from '@angular/core';
import {ConfirmCardSetupData, StripeElement, StripeElements} from "@stripe/stripe-js";
import {StripeService} from "ngx-stripe";
import {Authentication} from "../../backend/authentication/authentication";
import {PaymentsService} from "../../backend/payments/payments.service";
import {HttpLoadingService} from "../../backend/http-loading.service";
import {UserState} from "../../model/user-state";
import {CreateConnectedAccountResponse} from "../../backend/payments/response/create-connected-account-response";
import {CreateConnectedAccountLinkRequest} from "../../backend/payments/request/create-connected-account-link-request";
import {
  CreateConnectedAccountLinkResponse
} from "../../backend/payments/response/create-connected-account-link-response";

@Component({
  selector: 'app-regiser-card-details',
  templateUrl: './register-card-details.component.html',
  styleUrls: ['./register-card-details.component.css']
})
export class RegisterCardDetailsComponent implements OnInit {
  card: StripeElement;
  elements: StripeElements;

  private clientSecret: string | null | undefined;

  constructor(
    private stripeService: StripeService,
    private auth: Authentication,
    private paymentsService: PaymentsService,
    public httpLoader: HttpLoadingService,
  ){}

  ngOnInit(): void {
    this.setupStripeElements();

    if(this.auth.getUserState() == UserState.SIGNUP_OAUTH_CREDIT_CARD_COMPLETED){
      this.loadStripeConnectedAccountAndGetLink();
    }else{
      this.setupIntent();
    }
  }

  private async loadStripeConnectedAccountAndGetLink() {
    try{
      this.httpLoader.isLoading.next(true);

      console.log("HOla");

      const connectedAccountId = await this.paymentsService.getConnectedAccountId()
        .toPromise();
      // @ts-ignore

      console.log(connectedAccountId);

      // @ts-ignore
      await this.createStripeConnecetdAccountLink(connectedAccountId.connectedAcccountId);
    }catch (error){
      console.log(error);
    }
  }

  private async setupIntent() {
    const setupIntent = await this.paymentsService.setupIntent().toPromise();

    if(!setupIntent?.error){
      // @ts-ignore
      this.clientSecret = setupIntent.client_secret;
    }else{
      window.alert("Some error happened try later");
      await this.setupIntent();
    }
  }

  private setupStripeElements(): void {
    this.stripeService.elements({locale: 'es'}).subscribe(elements => {
      this.elements = elements;

      // Only mount the element the first time
      if (!this.card) {
        this.card = this.elements.create('card');
        this.card.mount('#card-element');
      }
    });
  }

  async registerCarDetails() {
    this.httpLoader.isLoading.next(true);

    // @ts-ignore
    const confirmCardResult = await this.stripeService.confirmCardSetup(this.clientSecret, this.buildConfirmCardSetupRequest())
      .toPromise();

    const confirmCardResultNotNull = confirmCardResult != undefined && confirmCardResult.setupIntent
      && confirmCardResult.setupIntent.payment_method != null;

    if(confirmCardResultNotNull){
      try{
        // @ts-ignore
        await this.createStripeCustomerAndConnectedAccount(confirmCardResult.setupIntent.payment_method);
        // @ts-ignore
        const connectedAccount: CreateConnectedAccountResponse = await this.paymentsService.createConnectedAccount()
          .toPromise();

        await this.createStripeConnecetdAccountLink(connectedAccount.connectedAcocuntId);
      }catch (error){
        window.alert("Error try later");
      }
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

  private async createStripeCustomerAndConnectedAccount(paymentMethod: string) {
    await this.paymentsService.createCustomer({paymentMethod: paymentMethod})
      .toPromise();
  }

  private async createStripeConnecetdAccountLink(connectedAccountId: string) {
    try {
      // @ts-ignore
      const connectedAccountLink: CreateConnectedAccountLinkResponse = await this.paymentsService.createConnectedAccountLink(
        {connectedAccountId: connectedAccountId}
      )
        .toPromise();

      this.httpLoader.isLoading.next(false);
      this.auth.setUserState(UserState.SIGNUP_OAUTH_CREDIT_CARD_COMPLETED);

      // @ts-ignore
      this.goToLink(connectedAccountLink.link);
    }catch (error){
      console.log(error);
      window.alert("Error try later");
    }
  }

  public goToLink(url: string){
    window.open(url, "_self");
  }
}
