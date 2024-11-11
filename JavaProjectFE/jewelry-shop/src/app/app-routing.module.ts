import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './layouts/unauthen/login/login.component';
import { RegisterComponent } from './layouts/unauthen/register/register.component';
import { AuthenticateGuard } from './services/jwt/authenticate.guard';

const routes: Routes = [
  {
    "path":"login",
    component: LoginComponent
  },
  {
    "path":"register",
    component: RegisterComponent
  },
  { "path": 'user',
    loadChildren: () =>
      import('../app/pages/user/user.module').then((m) => m.UserModule),
  },
  {
    "path": 'admin',
    loadChildren: () =>
      import('../app/pages/admin/admin.module').then((m) => m.AdminModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
