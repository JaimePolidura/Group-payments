import { Injectable } from '@angular/core';
import {GoogleLoginProvider, SocialAuthService, SocialUser} from "angularx-social-login";

@Injectable({
  providedIn: 'root'
})
export class OauthService {
  private socialUser: SocialUser;
  private userLogged: SocialUser;
  private logged: boolean;

  constructor(
    private authService: SocialAuthService,
  ){}

  subscribeToAuthState(callback: (() => void)): void {
    this.authService.authState.subscribe(
      data => {
        this.userLogged = data;
        this.logged = this.userLogged != null;

        callback();
      }
    );
  }

  signInWithGoogle(onSuccess: (() => void)): void{
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then(
      data => {
        this.socialUser = data;
        this.logged = true;

        onSuccess();
      }
    );
  }

  logout(onSuccess: () => void): void{
    this.authService.signOut().then(() => {
      this.logged = false;

      onSuccess();
    });
  }

  isLogged(): boolean {
    console.log(this.logged);

    return this.logged;
  }

}
