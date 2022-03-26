import {Injectable} from '@angular/core';
import {GoogleLoginProvider, SocialAuthService, SocialUser} from "angularx-social-login";
import {HttpClient} from "@angular/common/http";
import {LoginResponse} from "./responses/login-response";
import {LoginRequest} from "./request/login-request";
import {UserState} from "../../model/user/user-state";
import {Currency} from "../../model/currencies/currency";
import {PaymentsService} from "../payments/payments.service";

@Injectable({
  providedIn: 'root'
})
export class Authentication {
  private loggedUser: SocialUser;
  private userId: string;
  private token: string;
  private userState: UserState;
  private countryCode: string;
  private currency: Currency;

  constructor(
    private oauthProvider: SocialAuthService,
    private http: HttpClient,
    private paymentsService: PaymentsService,
  ){}

  public async signInWithGoogle(onSuccess: ((loginResponse: LoginResponse) => void), onCurrencyNotSuported: (() => void)) {
    try{
      const dataFromOAuthProvider = await this.oauthProvider.signIn(GoogleLoginProvider.PROVIDER_ID);
      const loginResponse: LoginResponse = await this.verifyTokenAndGetJWT({username: dataFromOAuthProvider.name, token: dataFromOAuthProvider.idToken});

      this.loggedUser = dataFromOAuthProvider;
      this.userId = loginResponse.userId;
      this.token = loginResponse.token;
      this.userState = loginResponse.userState;
      this.countryCode = loginResponse.countryCode;
      // @ts-ignore
      this.currency = await this.getCurrencyFromApi(this.countryCode);

      onSuccess(loginResponse);
    }catch(exception){
      console.error(exception);
    }
  }

  private async verifyTokenAndGetJWT(loginRequest: LoginRequest): Promise<any | LoginResponse>{
    return this.http.post<LoginResponse>('http://localhost:8080/auth/oauth/google', loginRequest)
      .toPromise();
  }

  private async getCurrencyFromApi(countryCode: string, onCurrencyNotSuported: () => void) {
    try{
      return await this.paymentsService.getCurrencyByCountryCode(countryCode)
        .toPromise();
    }catch (error) {
      onCurrencyNotSuported();

      return undefined;
    }
  }

  public logout(onSuccess: () => void): void{
    this.oauthProvider.signOut().then(() => {
      // @ts-ignore
      this.loggedUser = undefined;

      onSuccess();
    });
  }

  public isLogged(): boolean {
    return this.loggedUser != undefined;
  }

  public getName(): string {
    return this.loggedUser.name;
  }

  public getPhotoURL(): string{
    return this.loggedUser.photoUrl;
  }

  public getUserState(): UserState{
    return this.userState;
  }

  public getToken(): string{
    return this.token;
  }

  public setUserState(newUserState: UserState): void {
    this.userState = newUserState;
  }

  public getCurrency(): Currency {
    return this.currency;
  }

  public getUserId(): string {
    return this.userId;
  }

}
