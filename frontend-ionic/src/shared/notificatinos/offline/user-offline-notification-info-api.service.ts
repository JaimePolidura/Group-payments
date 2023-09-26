import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BackendUsingRoutesService} from "../../_shared/backend-using-routes.service";
import {RegisterOfflineNotificationUserInfo} from "./api/request/register-offline-notification-user-info";

@Injectable({
  providedIn: 'root'
})
export class UserOfflineNotificationInfoApiService {

  constructor(
    private http: HttpClient,
    private backendRoutes: BackendUsingRoutesService
  ) { }

  public register(req: RegisterOfflineNotificationUserInfo): Observable<any> {
    return this.http.post(`${this.backendRoutes.USING}/notifications/offline/register`, req);
  }
}
