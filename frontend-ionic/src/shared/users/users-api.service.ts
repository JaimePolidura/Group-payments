import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BackendUsingRoutesService} from "../_shared/backend-using-routes.service";
import {GetUserIdByEmailResponse} from "./api/response/get-user-id-by-email-response";
import {GetUserDataByUserId} from "./api/response/get-user-data-by-user-id";
import {RecentUsersRepositoryCacheService} from "../recentusers/recent-users-repository-cache.service";
import {EditUserRequest} from "./api/request/edit-user-request";
import {ConfirmDeleteAccountRequest} from "./api/request/confirm-delete-account-request";
import {tap} from "rxjs/operators";
import {ChangeUserImageResponse} from "./api/response/change-user-image-response";

@Injectable({
  providedIn: 'root'
})
export class UsersApiService {

  constructor(
    private http: HttpClient,
    private routes: BackendUsingRoutesService,
    private usersCache: RecentUsersRepositoryCacheService,
  ){}

  public getUserIdByEmail(email: string): Observable<GetUserIdByEmailResponse> {
    return this.http.get<GetUserIdByEmailResponse>(`${this.routes.USING}/users/getuseridbyemail?email=${email}`);
  }

  public getUserDataByUserIdId(userId: string): Observable<GetUserDataByUserId> {
    return this.http.get<GetUserDataByUserId>(`${this.routes.USING}/users/getuserdatabyuserid?userId=${userId}`).pipe(tap(userDataResponse => {
      this.usersCache.save({...userDataResponse, userId: userId});
    }));
  }

  public changeUserImage(file: File): Observable<ChangeUserImageResponse>{
    return this.http.post<ChangeUserImageResponse>(`${this.routes.USING}/usersimage/change`, file);
  }

  public editUser(request: EditUserRequest): Observable<any> {
    return this.http.post(`${this.routes.USING}/users/edit`, request);
  }

  public prepareDeleteAccount(): Observable<any> {
    return this.http.post(`${this.routes.USING}/users/delete/prepare`, {});
  }

  public confirmDeleteAccont(req: ConfirmDeleteAccountRequest): Observable<any> {
    return this.http.post(`${this.routes.USING}/users/delete/confirm`, req);
  }
}
