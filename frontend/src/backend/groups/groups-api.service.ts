import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {GetCurrentGroupResponse} from "./response/get-current-group-response";
import {GetGroupByIdResponse} from "./response/get-group-by-id-response";
import {JoinGroupResponse} from "./response/join-group-response";
import {JoinGroupRequest} from "./request/join-group-request";
import {CreateGroupResponse} from "./response/create-group-response";
import {CreateGroupRequest} from "./request/create-group-request";
import {GetGroupMembersByGroupIdResponse} from "./response/get-group-members-by-group-id-response";
import {LeaveGroupRequest} from "./request/leave-group-request";
import {KickGroupMemberRequest} from "./request/kick-group-member-request";
import {GroupPaymentRequest} from "./request/group-payment-request";
import {GetGroupMemberByUserIdRequest} from "./request/get-group-member-by-user-id-request";
import {GetGroupMemberByUserIdResponse} from "./response/get-group-member-by-user-id-response";
import {BackendUsingRoutesService} from "../backend-using-routes.service";
import {EditGroupRequest} from "./request/edit-group-request";

@Injectable({
  providedIn: 'root'
})
export class GroupsApiService {

  constructor(
    private http: HttpClient,
    private routes: BackendUsingRoutesService,
  ) {}

  public getCurrentGroup(userId: string): Observable<GetCurrentGroupResponse> {
    return this.http.get<GetCurrentGroupResponse>(`${this.routes.USING}/groups/current`);
  }

  public getGroupById(groupId: string): Observable<GetGroupByIdResponse>{
    return this.http.get<GetGroupByIdResponse>(`${this.routes.USING}/groups/id/${groupId}`);
  }

  public joinGroup(joinGroupRequest: JoinGroupRequest): Observable<JoinGroupResponse> {
    return this.http.post<JoinGroupResponse>(`${this.routes.USING}/groups/join`, joinGroupRequest);
  }

  public createGroup(createGroupRequest: CreateGroupRequest): Observable<CreateGroupResponse> {
    return this.http.post<CreateGroupResponse>(`${this.routes.USING}/groups/create`, createGroupRequest);
  }

  public getGroupMembersByGroupId(groupId: string): Observable<GetGroupMembersByGroupIdResponse> {
    return this.http.get<GetGroupMembersByGroupIdResponse>(`${this.routes.USING}/groups/members/${groupId}`);
  }

  public leaveGroup(request: LeaveGroupRequest): Observable<any>{
    return this.http.post(`${this.routes.USING}/groups/leave`, request);
  }

  public kickGroupMember(request: KickGroupMemberRequest): Observable<any> {
    return this.http.post(`${this.routes.USING}/groups/kick`, request);
  }

  public getGroupMemberByUserId(request: GetGroupMemberByUserIdRequest): Observable<GetGroupMemberByUserIdResponse> {
    return this.http.get<GetGroupMemberByUserIdResponse>(`${this.routes.USING}/groups/member?userId=${request.userId}&groupId=${request.groupId}`);
  }

  public editGroup(request: EditGroupRequest): Observable<any> {
    return this.http.post(`${this.routes.USING}/groups/edit`, request);
  }

  public makeGroupPayment(request: GroupPaymentRequest): Observable<any> {
    return this.http.post(`${this.routes.USING}/groups/payment`, request);
  }
}
