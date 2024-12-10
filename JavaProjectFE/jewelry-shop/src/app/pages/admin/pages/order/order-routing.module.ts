import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { OrderListComponent } from './order-list/order-list.component';

const routes: Routes = [
  {
    path:"",
    component: OrderListComponent
  },
  // {
  //   path:"create",
  //   component: 
  // },
  // {
  //   path:"edit",
  //   component: 
  // },
  

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrderRoutingModule { }
