import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {GetSupportedCurrenciesResponse} from "./api/response/get-supported-currencies-response";
import {BackendUsingRoutesService} from "../_shared/backend-using-routes.service";
import {tap} from "rxjs/operators";
import {Currency} from "./model/currency";

@Injectable({
  providedIn: 'root'
})
export class CurrenciesRepositoryApiService {
  constructor(
    private http: HttpClient,
    private backendRoutes: BackendUsingRoutesService,
  ) { }

  public findAll(): Observable<GetSupportedCurrenciesResponse> {
    return this.http.get<GetSupportedCurrenciesResponse>(`${this.backendRoutes.USING}/users/supportedcurrencies`);
  }
}
