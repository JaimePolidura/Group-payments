import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BackendUsingRoutesService {
  private LOCALHOST: string = "http://localhost:8080";
  private HOME_IP: string = "http://192.168.1.161:8080";
  private EXTRA: string = "http://192.168.1.179:8080";

  public USING: string = this.LOCALHOST;

  constructor() { }
}
