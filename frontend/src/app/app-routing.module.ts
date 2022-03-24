import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {MainComponent} from "./main/main.component";
import {UserStateSignUpAllCompleted} from "./guards/user-state-sign-up-all-completed.service";
import {JoinGroupComponent} from "./join-group/join-group.component";
import {
  RegisterCardDetailsComponent
} from "./regiser-card-details/register-card-details.component";
import {
  UserStateOAuthCompleted
} from "./guards/user-state-oauth-completed.service";
import {NeedsToBeAuthenticated} from "./guards/needs-to-be-authenticated.service";

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: '*', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'login/groupToJoin/:groupId', component: LoginComponent},
  {path: 'register', component: RegisterCardDetailsComponent, canActivate: [NeedsToBeAuthenticated, UserStateOAuthCompleted]},
  {path: 'main', component: MainComponent, canActivate: [NeedsToBeAuthenticated, UserStateSignUpAllCompleted]},
  {path: 'join/:groupId', component: JoinGroupComponent, canActivate: [NeedsToBeAuthenticated, UserStateSignUpAllCompleted]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
