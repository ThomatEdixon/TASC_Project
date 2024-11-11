import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductRoutingModule } from './product-routing.module';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ProductListComponent } from './product-list/product-list.component';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { productKey, productReducer } from './state/product.reducer';
import { ProductEffects } from './state/product.effects';
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  imports: [
    CommonModule,
    ProductRoutingModule,
    NgxPaginationModule,
    StoreModule.forFeature(productKey, productReducer),
    EffectsModule.forFeature([ProductEffects])
  ],
  declarations: [ProductDetailComponent,ProductListComponent]
})
export class ProductModule { }
