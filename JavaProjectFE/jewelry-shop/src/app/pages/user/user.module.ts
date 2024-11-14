import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserComponent } from './user.component';
import { HomepageComponent } from './layouts/homepage/homepage.component';
import { HeaderComponent } from './layouts/header/header.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { UserRoutingModule } from './user-routing.module';
import { ProductsComponent } from './pages/products/products.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    UserRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [UserComponent,HomepageComponent,HeaderComponent,FooterComponent,ProductsComponent]
})
export class UserModule { }
