import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BackendUsingRoutesService} from "../backend-using-routes.service";
import {GetUserIdByEmailResponse} from "./response/get-user-id-by-email-response";

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(
    private http: HttpClient,
    private routes: BackendUsingRoutesService,
  ){}

  public getUserIdByEmail(email: string): Observable<GetUserIdByEmailResponse> {
    return this.http.get<GetUserIdByEmailResponse>(`${this.routes.USING}/users/getuseridbyemail?email=${email}`);
  }
}
