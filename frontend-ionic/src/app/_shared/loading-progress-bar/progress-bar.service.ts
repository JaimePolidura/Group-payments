import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProgressBarService {
  public isLoading: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor() { }

  public start(): void {
    this.isLoading.next(true);
  }

  public stop(): void{
    this.isLoading.next(false);
  }
}
