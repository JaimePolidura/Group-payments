import { Injectable } from '@angular/core';
import {GoogleLoginProvider, SocialAuthService, SocialUser} from "angularx-social-login";
import {HttpClient, HttpClientModule} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class Authentication {
  private loggedUser: SocialUser;
  private logged: boolean;

  constructor(
    private oauthProvider: SocialAuthService,
    private http: HttpClient,
  ){
    this.logged = false;
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

  async signInWithGoogle(onSuccess: (() => void)) {
    const dataFromOAuthProvider = await this.oauthProvider.signIn(GoogleLoginProvider.PROVIDER_ID);
    const responseFromBackend = await this.verifyTokenAndGetJWT(dataFromOAuthProvider.idToken);

  
  }

  async verifyTokenAndGetJWT(tokenFromOauthProvider: string){
    this.http.post('http://localhost:8080/oauth/google', {token: tokenFromOauthProvider});
  }

  logout(onSuccess: () => void): void{
    this.oauthProvider.signOut().then(() => {
      this.logged = false;

      onSuccess();
    });
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
