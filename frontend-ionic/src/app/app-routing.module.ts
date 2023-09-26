import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {MainComponent} from "./main/main.component";
import {UserStateSignUpAllCompleted} from "./_shared/guards/user-state-sign-up-all-completed.service";
import {
  RegisterCardDetailsComponent
} from "./regiser-card-details/register-card-details.component";
import {
  UserStateOAuthCompleted
} from "./_shared/guards/user-state-oauth-completed.service";
import {NeedsToBeAuthenticated} from "./_shared/guards/needs-to-be-authenticated.service";
import {PaymentsHistoryComponent} from "./main/non-group-options/payments-history/payments-history.component";
import {AccountSettingsComponent} from "./main/non-group-options/account-settings/account-settings.component";
import {DeleteAccountConfirmComponent} from "./delete-account-confirm/delete-account-confirm.component";
import {ChangeCardConfirmComponent} from "./change-card-confirm/change-card-confirm.component";

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: '*', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'login/groupToJoin/:groupId', component: LoginComponent},
  {path: 'register', component: RegisterCardDetailsComponent, canActivate: [NeedsToBeAuthenticated, UserStateOAuthCompleted]},
  {path: 'main', component: MainComponent, canActivate: [NeedsToBeAuthenticated, UserStateSignUpAllCompleted]},
  {path: 'paymenthistory', component: PaymentsHistoryComponent, canActivate: [NeedsToBeAuthenticated, UserStateSignUpAllCompleted]},
  {path: 'accountsettings', component: AccountSettingsComponent, canActivate: [NeedsToBeAuthenticated, UserStateSignUpAllCompleted]},
  {path: 'delete', component: DeleteAccountConfirmComponent},
  {path: 'changecard', component: ChangeCardConfirmComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
