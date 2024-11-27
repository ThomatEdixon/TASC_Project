import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './user.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { ProductsComponent } from './pages/products/products.component';
import { ProductDetailComponent } from './pages/product-details/product-details.component';
import { OrderComponent } from './pages/order/order.component';
import { PaymentMethodComponent } from './pages/payment-method/payment-method.component';
import { UrlPayOSComponent } from './pages/urlPayOS/urlPayOS.component';

const routes: Routes = [
  {
    path: '',
    component: UserComponent,
    children:[
      {
        path:'home',
        component:HomepageComponent
      },
      {
        path:'products',
        component: ProductsComponent
      },
      {
        path:'product-detail',
        component: ProductDetailComponent
      },
      {
        path:'order',
        component: OrderComponent
      },
      {
        path:'payment-method',
        component:PaymentMethodComponent
      },
      {
        path:"payos?code = &id = &cancel = &status = &orderCode",
        component:UrlPayOSComponent
      }
    ]
  },
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule { }
