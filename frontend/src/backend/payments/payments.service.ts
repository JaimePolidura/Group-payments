import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {SetupIntentResult} from "@stripe/stripe-js";
import {HttpClient} from "@angular/common/http";
import {BackendUsingRoutesService} from "../backend-using-routes.service";

@Injectable({
  providedIn: 'root'
})
export class PaymentsService {

  constructor(private http: HttpClient,
              private usingRoutes: BackendUsingRoutesService
  ){}

  setupIntent(): Observable<SetupIntentResult> {
    return this.http.get<SetupIntentResult>(`${this.usingRoutes.USING}/payments/stripe/setupintent`);
  }
}
