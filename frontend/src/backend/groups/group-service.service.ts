import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {GetCurrentGroupResponse} from "./response/get-current-group-response";

@Injectable({
  providedIn: 'root'
})
export class GroupServiceService {

  constructor(private http: HttpClient) { }

  public getCurrentGroup(userId: string): Observable<GetCurrentGroupResponse> {
    return this.http.get<GetCurrentGroupResponse>("http://localhost:8080/groups/current");
  }
}
