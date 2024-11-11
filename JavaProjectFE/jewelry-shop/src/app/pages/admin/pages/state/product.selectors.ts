import { createFeatureSelector, createSelector } from '@ngrx/store';
import { productKey, ProductState } from './product.reducer';

export const selectProductState = createFeatureSelector<ProductState>(productKey);
export const selectListProduct = createSelector(selectProductState, state => state.products)
export const selectDetailDetail = createSelector(selectProductState, state => state.detailProduct)


