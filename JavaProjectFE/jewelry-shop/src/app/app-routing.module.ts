import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './layouts/unauthen/login/login.component';
import { RegisterComponent } from './layouts/unauthen/register/register.component';
import { HomepageComponent } from './layouts/homepage/homepage.component';

const routes: Routes = [
  {
    "path":"login",
    component: LoginComponent
  },
  {
    "path":"register",
    component: RegisterComponent
  },
  { "path": 'home', 
    component: HomepageComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
