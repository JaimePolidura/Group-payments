import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {MainComponent} from "./main/main.component";
import {ProtectedWithAuthGuard} from "./protected-with-auth.guard";
import {JoinGroupComponent} from "./join-group/join-group.component";
import {
  RegisterCardDetailsComponent
} from "./login/regiser-card-details/register-card-details.component";

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: '*', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'login/groupToJoin/:groupId', component: LoginComponent},
  {path: 'register', component: RegisterCardDetailsComponent, canActivate: []},
  {path: 'main', component: MainComponent, canActivate: [ProtectedWithAuthGuard]},
  {path: 'join/:groupId', component: JoinGroupComponent, canActivate: [ProtectedWithAuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
