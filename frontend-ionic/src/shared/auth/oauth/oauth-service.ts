import {OAuthProvider} from "./o-auth-provider";
import {OAuthResponse} from "./oauth-response";

export abstract class OAuthService {
  public abstract signIn(provider: OAuthProvider): Promise<OAuthResponse>;
  public abstract signOut(onSignedOut: () => void): void;
}
