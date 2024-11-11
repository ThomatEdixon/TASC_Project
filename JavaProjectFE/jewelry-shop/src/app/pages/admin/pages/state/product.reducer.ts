import { createReducer, on } from '@ngrx/store';
import { ProductAction } from './product.actions';

export interface ProductState {
  products: any;
  detailProduct: any;
  product:any;
}

export const initialState: ProductState = {
  products: null,
  detailProduct: null,
  product:null,
};
export const productKey = 'product'
export const productReducer = createReducer(
  initialState,
  on(ProductAction.getAllProducts, (state,) => ({ ...state})),
  on(ProductAction.getAllProductsFail, (state, { error }) => ({ ...state, error })),
  on(ProductAction.getAllProductsSuccess, (state, { success }) => ({ ...state, products: success })),

  on(ProductAction.getByIdProduct, (state,) => ({ ...state})),
  on(ProductAction.getByIdProductFail, (state, { error }) => ({ ...state, error })),
  on(ProductAction.getByIdProductSuccess, (state, { success }) => ({ ...state, detailProduct: success })),

  on(ProductAction.createProductt, (state,) => ({ ...state})),
  on(ProductAction.createProducttFail, (state, { error }) => ({ ...state, error })),
  on(ProductAction.createProducttSuccess, (state, { success }) => ({ ...state, product: success })),
  
);
