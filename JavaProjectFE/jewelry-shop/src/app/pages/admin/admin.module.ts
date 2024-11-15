import { NgModule } from '@angular/core';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { AdminComponent } from './admin.component';
import { AdminRoutingModule } from './admin-routing.module';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from './layouts/sidebar/sidebar.component';
import { DashboardComponent } from './layouts/dashboard/dashboard.component';

@NgModule({
  imports: [
    CommonModule,
    AdminRoutingModule,
  ],
  declarations: [AdminComponent,NavbarComponent,SidebarComponent,DashboardComponent]
})
export class AdminModule { }
