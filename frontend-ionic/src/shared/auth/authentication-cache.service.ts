import { Injectable } from '@angular/core';
import {SocialUser} from "angularx-social-login";
import {UserState} from "../users/model/user-state";
import {Currency} from "../currencies/model/currency";
import {OAuthResponse} from "./oauth/oauth-response";
import {User} from "../users/model/user";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationCacheService {
  private data: AuthenticationCacheData | undefined;

  constructor() { }

  public setData(data: AuthenticationCacheData): void {
    this.data = data;
  }

  public setUserState(userState: UserState): void {
    this.data = {
      token: this.data.token,
      user: {...this.data.user, state: userState}
    }
  }

  public setUserImageId(userImageId: number): void {
    this.data = {
      token: this.data.token,
      user: {...this.data.user, userImageId: userImageId}
    }
  }

  public clear(): void {
    this.data = undefined;
  }

  public getData(): AuthenticationCacheData {
    return <AuthenticationCacheData> this.data;
  }

  public get(key: keyof AuthenticationCacheData): any {
    return (<AuthenticationCacheData>this.data)[key];
  }

  public isLogged(): boolean {
    return this.data != undefined;
  }

  public getCountryCode(): string {
    return (<AuthenticationCacheData>this.data).user.country.toUpperCase();
  }

  public getUsername(): string {
    return (<AuthenticationCacheData>this.data).user.username;
  }

  public getUserImageId(): number{
    return (<AuthenticationCacheData>this.data).user.userImageId;
  }

  public getUserState(): UserState{
    return (<AuthenticationCacheData>this.data).user.state;
  }

  public getToken(): string{
    return (<AuthenticationCacheData>this.data).token;
  }

  public getCurrency(): Currency {
    return (<AuthenticationCacheData>this.data).user.currency;
  }

  public getUserId(): string {
    return (<AuthenticationCacheData>this.data).user.userId;
  }

  public getEmail(): string {
    return (<AuthenticationCacheData>this.data).user.email;
  }
}

export type AuthenticationCacheData = {
  user: User;
  token: string,
}
