import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {GetCurrentGroupResponse} from "./api/response/get-current-group-response";
import {GetGroupByIdResponse} from "./api/response/get-group-by-id-response";
import {JoinGroupResponse} from "./api/response/join-group-response";
import {JoinGroupRequest} from "./api/request/join-group-request";
import {CreateGroupResponse} from "./api/response/create-group-response";
import {CreateGroupRequest} from "./api/request/create-group-request";
import {LeaveGroupRequest} from "./api/request/leave-group-request";
import {KickGroupMemberRequest} from "./api/request/kick-group-member-request";
import {GroupPaymentRequest} from "./api/request/group-payment-request";
import {GetGroupMemberByUserIdRequest} from "./api/request/get-group-member-by-user-id-request";
import {GetGroupMemberByUserIdResponse} from "./api/response/get-group-member-by-user-id-response";
import {BackendUsingRoutesService} from "../_shared/backend-using-routes.service";
import {EditGroupRequest} from "./api/request/edit-group-request";
import {GroupCacheService} from "./group-cache.service";
import {Group} from "./model/group";
import {User} from "../users/model/user";
import {tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class GroupsApiService {

  constructor(
    private http: HttpClient,
    private routes: BackendUsingRoutesService,
    private groupsCache: GroupCacheService,
  ) {}

  public getCurrentGroup(userId: string): Observable<GetCurrentGroupResponse> {
    return this.http.get<GetCurrentGroupResponse>(`${this.routes.USING}/groups/current`).pipe(tap(response => {
      this.addGroupAndMembersToCache(response);
    }));
  }

  public getGroupById(groupId: string): Observable<GetGroupByIdResponse>{
    return this.http.get<GetGroupByIdResponse>(`${this.routes.USING}/groups/id/${groupId}`);
  }

  public joinGroup(joinGroupRequest: JoinGroupRequest): Observable<JoinGroupResponse> {
    return this.http.post<JoinGroupResponse>(`${this.routes.USING}/groups/join`, joinGroupRequest).pipe(tap(response => {
      this.addGroupAndMembersToCache(response);
    }));
  }

  public createGroup(createGroupRequest: CreateGroupRequest): Observable<CreateGroupResponse> {
    return this.http.post<CreateGroupResponse>(`${this.routes.USING}/groups/create`, createGroupRequest).pipe(tap(response => {
      this.addGroupAndMembersToCache(response);
    }));
  }

  public leaveGroup(request: LeaveGroupRequest): Observable<any>{
    return this.http.post(`${this.routes.USING}/groups/leave`, request).pipe(tap(response => {
      this.groupsCache.clear();
    }));
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

  private addGroupAndMembersToCache(data: {group: Group, members: User[]}): void {
    this.groupsCache.setGroup(data.group);
    this.groupsCache.setMembers(data.members);
  }
}
