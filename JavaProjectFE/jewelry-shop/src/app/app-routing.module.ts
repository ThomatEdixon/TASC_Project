import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './layouts/unauthen/login/login.component';
import { RegisterComponent } from './layouts/unauthen/register/register.component';
import { ForgotPasswordComponent } from './layouts/unauthen/forgot-password/forgot-password.component';
import { VerifyComponent } from './layouts/unauthen/verify/verify.component';
import { ChangePasswordComponent } from './layouts/unauthen/change-password/change-password.component';

const routes: Routes = [
  {
    path:"login",
    component: LoginComponent
  },
  {
    path:"register",
    component: RegisterComponent
  },
  {
    path:"forgot-password",
    component: ForgotPasswordComponent
  },
  {
    path:"verify",
    component: VerifyComponent
  },
  {
    path:"change-password",
    component: ChangePasswordComponent
  },
  { path: 'user',
    loadChildren: () =>
      import('../app/pages/user/user.module').then((m) => m.UserModule),
  },
  {
    path: 'admin',
    loadChildren: () =>
      import('../app/pages/admin/admin.module').then((m) => m.AdminModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
