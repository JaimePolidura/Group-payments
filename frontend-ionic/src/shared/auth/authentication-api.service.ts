import {Injectable} from '@angular/core';
import {SocialUser} from "angularx-social-login";
import {HttpClient} from "@angular/common/http";
import {LoginResponse} from "./api/responses/login-response";
import {LoginRequest} from "./api/request/login-request";
import {PaymentsApiService} from "../payments/payments-api.service";
import {BackendUsingRoutesService} from "../_shared/backend-using-routes.service";
import {AuthenticationCacheService} from "./authentication-cache.service";
import {OAuthService} from "./oauth/oauth-service";
import {OAuthProvider} from "./oauth/o-auth-provider";
import {OAuthResponse} from "./oauth/oauth-response";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationApiService {
  constructor(
    private oauth: OAuthService,
    private http: HttpClient,
    private paymentsService: PaymentsApiService,
    private backendRoutes: BackendUsingRoutesService,
    private authCache: AuthenticationCacheService,
  ){}

  public async signInWithGoogle(onSuccess: ((loginResponse: LoginResponse) => void), onCurrencyNotSuported: (() => void)) {
    try{
      const dataFromOAuthProvider = await this.oauth.signIn(OAuthProvider.GOOGLE);

      const loginResponse: LoginResponse = await this.verifyTokenAndGetJWT({
        username: dataFromOAuthProvider.name,
        token: dataFromOAuthProvider.token
      });

      this.authCache.setData({
        user: loginResponse.user,
        token: loginResponse.token
      });

      onSuccess(loginResponse);
    }catch(exception){
      throw new Error();
    }
  }

  private async verifyTokenAndGetJWT(loginRequest: LoginRequest): Promise<any | LoginResponse>{
    return this.http.post<LoginResponse>(this.backendRoutes.USING + '/auth/oauth/google', loginRequest)
      .toPromise();
  }

  public logout(onSuccess: () => void): void{
    this.oauth.signOut(() => {
      this.authCache.clear();

      onSuccess();
    });
  }

}
