import { Injectable } from '@angular/core';
import { OAuthProvider } from '../o-auth-provider';
import {OAuthService} from "../oauth-service";
import {OAuthResponse} from "../oauth-response";
import '@codetrix-studio/capacitor-google-auth';
import {GoogleAuth} from "@codetrix-studio/capacitor-google-auth";
import {isPlatform} from "@ionic/angular";

@Injectable({
  providedIn: 'root'
})
export class CapacitorGoogleOAuthServiceService implements OAuthService{
  constructor() {
    if(!isPlatform('capacitor')){
      GoogleAuth.initialize();
    }
  }

  public async signIn(provider: OAuthProvider): Promise<OAuthResponse> {
    const user = await GoogleAuth.signIn();

    return new Promise<OAuthResponse>((resolve, reject) => {
      resolve({
        provider: provider,
        name: user.name,
        token: user.authentication.idToken,
        email: user.email,
        photoUrl: user.imageUrl
      });
    });
  }

  public signOut(onSignedOut: () => void): void {
    GoogleAuth.signOut().then(() => {
      onSignedOut();
    });
  }
}
