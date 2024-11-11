import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './user.component';
import { HomepageComponent } from './layouts/homepage/homepage.component';

const routes: Routes = [
  {
    path: '',
    component: UserComponent,
  },
  {
    path:'home',
    component: HomepageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule { }
