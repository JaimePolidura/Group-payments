import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {SetupIntentResult} from "@stripe/stripe-js";
import {HttpClient} from "@angular/common/http";
import {BackendUsingRoutesService} from "../backend-using-routes.service";
import {CreateCustomerRequest} from "./request/create-customer-request";
import {CreateConnectedAccountResponse} from "./response/create-connected-account-response";
import {MakePaymentRequest} from "../groups/request/make-payment-request";
import {CreateConnectedAccountLinkRequest} from "./request/create-connected-account-link-request";
import {CreateConnectedAccountLinkResponse} from "./response/create-connected-account-link-response";
import {GetConnectedAccountIdResponse} from "./response/get-connected-account-id-response";

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

  public createCustomer(request: CreateCustomerRequest): Observable<any> {
    return this.http.post(`${this.usingRoutes.USING}/payments/stripe/createcustomer`, request);
  }

  public createConnectedAccount(): Observable<CreateConnectedAccountResponse> {
    return this.http.post<CreateConnectedAccountResponse>(`${this.usingRoutes.USING}/payments/stripe/createconnectedaccount`, {});
  }

  public makePayment(request: MakePaymentRequest): Observable<any> {
    return this.http.post(`${this.usingRoutes.USING}/payments/makepayment`, request);
  }

  public createConnectedAccountLink(request: CreateConnectedAccountLinkRequest): Observable<CreateConnectedAccountLinkResponse> {
    return this.http.post<CreateConnectedAccountLinkResponse>(`${this.usingRoutes.USING}/payments/stripe/createlink`, {...request, ignoreThis: ''});
  }

  public getConnectedAccountId(): Observable<GetConnectedAccountIdResponse> {
    return this.http.get<GetConnectedAccountIdResponse>(`${this.usingRoutes.USING}/payments/stripe/getconnectedaccountid`);
  }
}
