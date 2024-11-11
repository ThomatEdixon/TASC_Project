import { createActionGroup, props } from '@ngrx/store';

export const ProductAction = createActionGroup({
    source :"Product",
    events: {
        getAllProducts: props<{ page:number , size:number}>(),
        getAllProductsSuccess: props<{ success: any }>(),
        getAllProductsFail: props<{ error: any }>(),

        getByIdProduct: props<{ request: any }>(),
        getByIdProductSuccess: props<{ success: any }>(),
        getByIdProductFail: props<{ error: any }>(),

        createProductt: props<{ request: any }>(),
        createProducttSuccess: props<{ success: any }>(),
        createProducttFail: props<{ error: any }>(),
    }
}) 

