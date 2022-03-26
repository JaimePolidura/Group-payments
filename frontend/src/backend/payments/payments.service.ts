import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {SetupIntentResult} from "@stripe/stripe-js";
import {HttpClient} from "@angular/common/http";
import {BackendUsingRoutesService} from "../backend-using-routes.service";
import {MakePaymentRequest} from "../groups/request/make-payment-request";
import {RegisterWithStripeResponse} from "./response/register-with-stripe-response";
import {RegisterWithStripeRequest} from "./request/register-with-stripe-request";
import {GetConnectedAccountLinkResponse} from "./response/get-connected-account-link-response";
import {GetPaymentHistoryRequest} from "./request/get-payment-history-request";
import {GetPaymentsHistoryResponse} from "./response/get-payments-history-response";
import {Currency} from "../../model/currencies/currency";

@Injectable({
  providedIn: 'root'
})
export class PaymentsService {

  constructor(private http: HttpClient,
              private usingRoutes: BackendUsingRoutesService
  ){}

  public setupIntent(): Observable<SetupIntentResult> {
    return this.http.get<SetupIntentResult>(`${this.usingRoutes.USING}/payments/stripe/setupintent`);
  }

  public makePayment(request: MakePaymentRequest): Observable<any> {
    return this.http.post(`${this.usingRoutes.USING}/payments/makepayment`, request);
  }

  public registerWithStripe(request: RegisterWithStripeRequest): Observable<RegisterWithStripeResponse> {
    return this.http.post<RegisterWithStripeResponse>(`${this.usingRoutes.USING}/payments/stripe/register`, {...request, ignoreThis: ''});
  }

  public getConnectedAccuntLink(): Observable<GetConnectedAccountLinkResponse> {
    return this.http.get<GetConnectedAccountLinkResponse>(`${this.usingRoutes.USING}/payments/stripe/getconnectedaccountlink`);
  }

  public getPaymentHistory(req: GetPaymentHistoryRequest): Observable<GetPaymentsHistoryResponse> {
    return this.http.get<GetPaymentsHistoryResponse>(`${this.usingRoutes.USING}/payments/paymentshistory?pageNumber=${req.pageNumber}&itemsPerPage=${req.itemsPerPage}&paymentType=${req.paymentTypeSearch}`);
  }

  public getCurrencyByCountryCode(countryCode: string): Observable<Currency>{
    return this.http.get<Currency>(`${this.usingRoutes.USING}/payments/currencies/getbycountrycode?countryCode=${countryCode}`);
  }
}
