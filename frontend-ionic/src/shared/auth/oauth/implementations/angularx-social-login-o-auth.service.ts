import { Injectable } from '@angular/core';
import {OAuthService} from "../oauth-service";
import {OAuthProvider} from "../o-auth-provider";
import {OAuthResponse} from "../oauth-response";
import {GoogleLoginProvider, SocialAuthService} from "angularx-social-login";

@Injectable({
  providedIn: 'root'
})
export class AngularxSocialLoginOAuthService implements OAuthService{

  constructor(
    private angularxSocialLogin: SocialAuthService
  ) {}

  signIn(providerName: OAuthProvider): Promise<OAuthResponse> {
    return <Promise<OAuthResponse>> this.angularxSocialLogin.signIn(GoogleLoginProvider.PROVIDER_ID).then(res => {
      return {
        ...res,
        provider: providerName,
        token: res.idToken,
      }
    });
  }

  signOut(onSignedOut: () => void): void {
    this.angularxSocialLogin.signOut().then(() => {
      onSignedOut();
    });
  }
}
