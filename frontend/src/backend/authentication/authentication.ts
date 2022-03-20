import {Injectable} from '@angular/core';
import {GoogleLoginProvider, SocialAuthService, SocialUser} from "angularx-social-login";
import {HttpClient} from "@angular/common/http";
import {LoginResponse} from "./responses/login-response";
import {LoginRequest} from "./request/login-request";
import {UserState} from "../../model/user-state";

@Injectable({
  providedIn: 'root'
})
export class Authentication {
  private loggedUser: SocialUser;
  private userId: string;
  private token: string;
  private userState: UserState;

  constructor(
    private oauthProvider: SocialAuthService,
    private http: HttpClient,
  ){}

  public async signInWithGoogle(onSuccess: ((loginResponse: LoginResponse) => void)) {
    try{
      const dataFromOAuthProvider = await this.oauthProvider.signIn(GoogleLoginProvider.PROVIDER_ID);
      const loginResponse = await this.verifyTokenAndGetJWT({username: dataFromOAuthProvider.name, token: dataFromOAuthProvider.idToken});

      this.loggedUser = dataFromOAuthProvider;
      this.userId = loginResponse.userId;
      this.token = loginResponse.token;
      this.userState = loginResponse.userState;

      onSuccess(loginResponse);
    }catch(exception){
      window.alert("ERROR");
      console.error(exception);
    }
  }

  public async verifyTokenAndGetJWT(loginRequest: LoginRequest): Promise<any | LoginResponse>{
    return this.http.post<LoginResponse>('http://localhost:8080/auth/oauth/google', loginRequest)
      .toPromise();
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

  public getUserId(): string {
    return this.userId;
  }

}
