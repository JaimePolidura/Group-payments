import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ExistsByEmailResponse} from "./response/exists-by-email-response";
import {BackendUsingRoutesService} from "../backend-using-routes.service";

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(
    private http: HttpClient,
    private routes: BackendUsingRoutesService,
  ){}

  public existsByEmail(email: string): Observable<ExistsByEmailResponse> {
    return this.http.get<ExistsByEmailResponse>(`${this.routes.USING}/users/existsbyemail?email=${email}`);
  }
}
