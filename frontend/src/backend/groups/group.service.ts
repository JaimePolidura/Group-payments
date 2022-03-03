import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {GetCurrentGroupResponse} from "./response/get-current-group-response";
import {GetGroupByIdResponse} from "./response/get-group-by-id-response";
import {JoinGroupResponse} from "./response/join-group-response";
import {JoinGroupRequest} from "./request/join-group-request";

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  constructor(private http: HttpClient) { }

  public getCurrentGroup(userId: string): Observable<GetCurrentGroupResponse> {
    return this.http.get<GetCurrentGroupResponse>("http://localhost:8080/groups/current");
  }

  public getGroupById(groupId: string): Observable<GetGroupByIdResponse>{
    return this.http.get<GetGroupByIdResponse>(`http://localhost:8080/groups/id/${groupId}`);
  }

  public joinGroup(joinGroupRequest: JoinGroupRequest): Observable<JoinGroupResponse> {
    return this.http.post<JoinGroupResponse>("http://localhost:8080/groups/join", joinGroupRequest);
  }
}
