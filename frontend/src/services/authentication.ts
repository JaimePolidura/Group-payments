import {Injectable} from '@angular/core';
import {GoogleLoginProvider, SocialAuthService, SocialUser} from "angularx-social-login";
import {HttpClient} from "@angular/common/http";
import {LoginResponse} from "../models/login-response";

@Injectable({
  providedIn: 'root'
})
export class Authentication {
  private loggedUser: SocialUser;
  private logged: boolean = false;
  private userId: string;
  private token: string;

  constructor(
    private oauthProvider: SocialAuthService,
    private http: HttpClient,
  ){}

  async signInWithGoogle(onSuccess: (() => void)) {
    try{
      const dataFromOAuthProvider = await this.oauthProvider.signIn(GoogleLoginProvider.PROVIDER_ID);
      const responseFromBackend = await this.verifyTokenAndGetJWT(dataFromOAuthProvider.idToken, dataFromOAuthProvider.name);

      this.logged = true;
      this.loggedUser = dataFromOAuthProvider;
      this.userId = responseFromBackend.userId;
      this.token = responseFromBackend.token;

      onSuccess();
    }catch(exception){
      window.alert("ERROR");
      console.error(exception);
    }
  }

  async verifyTokenAndGetJWT(tokenFromOauthProvider: string, name: string): Promise<any |  LoginResponse>{
    return this.http.post<LoginResponse>('http://localhost:8080/oauth/google', {token: tokenFromOauthProvider, username: name})
      .toPromise();
  }

  logout(onSuccess: () => void): void{
    this.oauthProvider.signOut().then(() => {
      this.logged = false;

      onSuccess();
    });
  }

  subscribeToAuthState(callback: (() => void)): void {
    this.oauthProvider.authState.subscribe(
      data => {
        this.loggedUser = data;
        this.logged = this.loggedUser != null;

        callback();
      }
    );
  }

  isLogged(): boolean {
    return this.logged;
  }

  getName(): string {
    return this.loggedUser.name;
  }

  getPhotoURL(): string{
    return this.loggedUser.photoUrl;
  }

}
