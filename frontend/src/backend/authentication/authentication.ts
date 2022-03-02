import {Injectable} from '@angular/core';
import {GoogleLoginProvider, SocialAuthService, SocialUser} from "angularx-social-login";
import {HttpClient} from "@angular/common/http";
import {LoginResponse} from "./responses/login-response";
import {LoginRequest} from "./request/login-request";

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
      const responseFromBackend = await this.verifyTokenAndGetJWT({username: dataFromOAuthProvider.name, token: dataFromOAuthProvider.idToken});

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

  async verifyTokenAndGetJWT(loginRequest: LoginRequest): Promise<any | LoginResponse>{
    return this.http.post<LoginResponse>('http://localhost:8080/oauth/google', loginRequest)
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

  getToken(): string{
    return this.token;
  }

}
