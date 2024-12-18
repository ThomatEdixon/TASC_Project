import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import { DashboardComponent } from './layouts/dashboard/dashboard.component';

const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    children: [
      {
        path: "product",
        loadChildren: () =>
          import('./pages/product/product.module').then((m) => m.ProductModule),
      },
      {
        path: "order",
        loadChildren: () =>
          import('./pages/order/order.module').then((m) => m.OrderModule),
      },
      {
        path: "dashboard",
        component:DashboardComponent
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule { }
