import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {SetupIntentResult} from "@stripe/stripe-js";
import {HttpClient} from "@angular/common/http";
import {BackendUsingRoutesService} from "../_shared/backend-using-routes.service";
import {RegisterWithStripeResponse} from "./api/response/register-with-stripe-response";
import {RegisterWithStripeRequest} from "./api/request/register-with-stripe-request";
import {GetConnectedAccountLinkResponse} from "./api/response/get-connected-account-link-response";
import {GetPaymentHistoryRequest} from "./api/request/get-payment-history-request";
import {GetPaymentsHistoryResponse} from "./api/response/get-payments-history-response";
import {MakeTransferRquest} from "./api/request/make-transfer-rquest";
import {GetCommissionPolicyResponse} from "./api/response/get-commission-policy-response";
import {ChangeCardRequest} from "./api/request/change-card-request";
import {ConfirmChangeCardRequest} from "./api/request/confirm-change-card-request";

@Injectable({
  providedIn: 'root'
})
export class PaymentsApiService {

  constructor(private http: HttpClient,
              private usingRoutes: BackendUsingRoutesService,
  ){}

  public setupIntent(): Observable<SetupIntentResult> {
    return this.http.get<SetupIntentResult>(`${this.usingRoutes.USING}/payments/stripe/setupintent`);
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

  public makeTransfer(transfer: MakeTransferRquest): Observable<any>{
    return this.http.post(`${this.usingRoutes.USING}/payments/transfer`, transfer);
  }

  public prepareChangeCard(req: ChangeCardRequest): Observable<any> {
    return this.http.post(`${this.usingRoutes.USING}/payments/stripe/changecard/prepare`, req, {responseType: 'text'});
  }

  public confirmChangeCard(req: ConfirmChangeCardRequest): Observable<any>{
    return this.http.post(`${this.usingRoutes.USING}/payments/stripe/changecard/confirm`, req, {responseType: 'text'});
  }

  public getCommissionPolicy(): Observable<GetCommissionPolicyResponse> {
    return this.http.get<GetCommissionPolicyResponse>(`${this.usingRoutes.USING}/payments/commissionpolicy`);
  }
}



