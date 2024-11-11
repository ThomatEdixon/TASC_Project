import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, of, tap } from 'rxjs';
import { ProductService } from '../../../../services/product.service';
import { ProductAction } from './product.actions';

@Injectable()
export class ProductEffects {
  constructor(
    private actions$: Actions,
    private productService: ProductService
  ) { console.log(this.actions$)}
  products$ = createEffect(() =>
    this.actions$.pipe(
      ofType(ProductAction.getAllProducts),
      mergeMap(({ page, size }) =>
        this.productService.getProducts(page, size).pipe(
          tap((res)=> console.log(res)),
          map((res: any) => ProductAction.getAllProductsSuccess({ success: res })),
          catchError((error) => of(ProductAction.getAllProductsFail({ error })))
        )
      )
    )
  );
}
