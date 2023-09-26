import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ConfirmCardSetupData, StripeElement, StripeElements} from "@stripe/stripe-js";
import {StripeService} from "ngx-stripe";
import {PaymentsApiService} from "../../shared/payments/payments-api.service";
import {ProgressBarService} from "../_shared/loading-progress-bar/progress-bar.service";
import {UserState} from "../../shared/users/model/user-state";
import {AuthenticationCacheService} from "../../shared/auth/authentication-cache.service";
import {AuthenticationApiService} from "../../shared/auth/authentication-api.service";
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {RegisterWithStripeRequest} from "../../shared/payments/api/request/register-with-stripe-request";
import {RegisterWithStripeResponse} from "../../shared/payments/api/response/register-with-stripe-response";

const MAX_AGE = 100;
const MIN_AGE = 18;

@Component({
  selector: 'app-regiser-card-details',
  templateUrl: './register-card-details.component.html',
  styleUrls: ['./register-card-details.component.css']
})
export class RegisterCardDetailsComponent implements OnInit, AfterViewInit {
  card: StripeElement;
  elements: StripeElements;

  public registerForm: FormGroup;

  private clientSecret: string | null | undefined;

  @ViewChild('dobMonthInput') public dobMonthInput: ElementRef<HTMLInputElement>;
  @ViewChild('dobDayInput') public dobDayInput: ElementRef<HTMLInputElement>;
  @ViewChild('dobYearInput') public dobYearInput: ElementRef<HTMLInputElement>;

  constructor(
    private stripeService: StripeService,
    private authCache: AuthenticationCacheService,
    private authService: AuthenticationApiService,
    private paymentsService: PaymentsApiService,
    public httpLoader: ProgressBarService,
  ){}

  ngOnInit(): void {
    this.setupStripeElements();
    this.setupRegisterForm();

    if(this.authCache.getUserState() == UserState.SIGNUP_OAUTH_CREDIT_CARD_COMPLETED)
      this.loadStripeConnectedAccountAndGetLink();
    else
      this.setupIntent();
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.dobYearInput.nativeElement.focus();
    }, 0);
  }

  private async loadStripeConnectedAccountAndGetLink() {
    this.httpLoader.start();

    this.paymentsService.getConnectedAccuntLink().subscribe(res => {
      const link: string = res.link;

      this.authCache.setUserState(UserState.SIGNUP_OAUTH_CREDIT_CARD_COMPLETED);

      this.goToLink(link);
    });
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

      if (!this.card) {
        this.card = this.elements.create('card');
        this.card.mount('#card-element');
      }
    });
  }

  async registerCarDetails() {
    this.httpLoader.start();

    try{
      await this.tryToRegisterCardDetails();
    }catch (error){
      window.alert("sas");
      this.httpLoader.stop();
    }
  }

  private async tryToRegisterCardDetails() {
    //@ts-ignore
    const confirmCardResult = await this.stripeService.confirmCardSetup(this.clientSecret, this.buildConfirmCardSetupRequest())
      .toPromise();
    const confirmCardResultNull = confirmCardResult == undefined || !confirmCardResult.setupIntent
      || confirmCardResult.setupIntent.payment_method == null || !confirmCardResult.setupIntent.payment_method;

    if(confirmCardResultNull)
      throw new Error('Invalid card details');

    // @ts-ignore
    const paymentMethodRegister: string = confirmCardResult.setupIntent.payment_method;

    const requestToRegister: RegisterWithStripeRequest = {
      paymentMethod: paymentMethodRegister,
      dob: {
        year: this.year.value,
        month: this.month.value,
        day: this.day.value
      },
    };

    // @ts-ignore
    const result: RegisterWithStripeResponse = await this.paymentsService.registerWithStripe(requestToRegister)
      .toPromise();

    this.authCache.setUserState(UserState.SIGNUP_OAUTH_CREDIT_CARD_COMPLETED);

    this.goToLink(result.link);
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

  public onDobYearKeyup() {
    if(this.year.value > 999 && this.year.valid)
      this.dobMonthInput.nativeElement.focus();
  }

  public onDobMonthKeyup() {
    if (!this.month.valid ||this.month.value == 1) return;

    if (this.month.value >= 10)
      this.dobDayInput.nativeElement.focus();

    if(this.month.value > 1 && this.month.value <= 9)
      this.dobDayInput.nativeElement.focus();
  }

  public goToLink(url: string){
    window.open(url, "_self");
  }

  private setupRegisterForm() {
    const minYear = new Date().getFullYear() - MAX_AGE;
    const maxYear = new Date().getFullYear() - MIN_AGE;

    this.registerForm = new FormGroup({
      year: new FormControl('' ,[Validators.required, Validators.min(minYear), Validators.max(maxYear)]),
      month: new FormControl('', [Validators.required, Validators.min(1), Validators.max(12)]),
      day: new FormControl('', [Validators.required, Validators.min(1), Validators.max(31)])
    });
  }
  get year(): AbstractControl { return <AbstractControl>this.registerForm.get('year'); }
  get month(): AbstractControl { return <AbstractControl>this.registerForm.get('month'); }
  get day(): AbstractControl { return <AbstractControl>this.registerForm.get('day'); }

  public onDobDayKeyupBackspace(): void {
    if(this.day.value == null || !this.day.value){
      this.dobMonthInput.nativeElement.focus();
      this.dobDayInput.nativeElement.classList.remove("is-invalid");
    }
  }

  public onDobMonthKeyupBackspace(): void {
    if(this.month.value == null){
      this.dobMonthInput.nativeElement.classList.remove("is-invalid");
      this.dobYearInput.nativeElement.focus();
    }
  }
}
