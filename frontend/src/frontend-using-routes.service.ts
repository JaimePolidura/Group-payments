import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FrontendUsingRoutesService {
  private LOCALHOST: string = "http://localhost:4200";

  public USING: string = this.LOCALHOST;

  constructor() { }
}
