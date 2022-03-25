import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BackendUsingRoutesService {
  private LOCALHOST: string = "http://localhost:8080";
  private HOME_IP: string = "http://192.168.1.172:8080";

  public USING: string = this.HOME_IP;

  constructor() { }
}
