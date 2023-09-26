import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BackendUsingRoutesService} from "../_shared/backend-using-routes.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ImageUserRepositoryApiService {

  constructor(private http: HttpClient,
              private backend: BackendUsingRoutesService) { }

  public findById(imageId: number): Observable<Blob> {
    return this.http.get(`${this.backend.USING}/usersimage/get/${imageId}`, {responseType: 'blob'});
  }
}
