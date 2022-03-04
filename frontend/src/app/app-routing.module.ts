import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {MainComponent} from "./main/main.component";
import {ProtectedWithAuthGuard} from "./protected-with-auth.guard";

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: '*', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'main', component: MainComponent, canActivate: [ProtectedWithAuthGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
