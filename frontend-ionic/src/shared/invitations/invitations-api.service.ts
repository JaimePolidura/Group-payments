import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CreateInvitationRequest} from "./api/request/create-invitation-request";
import {BackendUsingRoutesService} from "../_shared/backend-using-routes.service";
import {AcceptInvitationRequest} from "./api/request/accept-invitation-request";
import {JoinGroupResponse} from "../groups/api/response/join-group-response";
import {GetInvitatinosByUserId} from "./api/response/get-invitatinos-by-user-id";
import {RejectInvitationRequest} from "./api/request/reject-invitation-request";
import {GroupCacheService} from "../groups/group-cache.service";
import {InvitationsCacheService} from "./invitations-cache.service";
import {tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class InvitationsApiService {
  constructor(
    private http: HttpClient,
    private backendRoutes: BackendUsingRoutesService,
    private groupCache: GroupCacheService,
    private invitationsCahce: InvitationsCacheService,
  ) { }

  public createInvitation(req: CreateInvitationRequest): Observable<void> {
    return this.http.post<void>(`${this.backendRoutes.USING}/invitations/create`, req);
  }

  public accpet(req: AcceptInvitationRequest): Observable<JoinGroupResponse> {
    return this.http.post<JoinGroupResponse>(`${this.backendRoutes.USING}/invitations/accept`, req).pipe(tap(response => {
      this.groupCache.clear();
      this.groupCache.setMembers(response.members);
      this.groupCache.setGroup(response.group);
      this.invitationsCahce.deleteById(req.invitationId);
    }));
  }

  public getInvitationsByUserId(): Observable<GetInvitatinosByUserId> {
    return this.http.get<GetInvitatinosByUserId>(`${this.backendRoutes.USING}/invitations/getbyuserid`).pipe(tap(response => {
      this.invitationsCahce.addAll(response.invitiations);
    }));
  }

  public reject(req: RejectInvitationRequest): Observable<void> {
    return this.http.post<void>(`${this.backendRoutes.USING}/invitations/reject`, req).pipe(tap(response => {
      this.invitationsCahce.deleteById(req.invitationId);
    }));
  }
}
