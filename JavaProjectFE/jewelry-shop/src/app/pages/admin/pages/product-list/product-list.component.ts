import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { ProductImage, ProductResponse } from '../../../../models/product';
import { ProductAction } from '../state/product.actions';
import * as productSelector from '../state/product.selectors'

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  constructor(private store:Store) { }

  products : ProductResponse[]=[];
  selectedImages: ProductImage[] | null = null;
  totalItems: number= 20;
  pageSize: number = 10;
  currentPage: number = 1;

  ngOnInit() {
    this.getAll();
    this.store.select(productSelector.selectListProduct).pipe().subscribe((res:any)=>{
      if(res.code == 100) this.products = res.map((x:any)=> x);
    })
  }
  closeModal() {
    this.selectedImages = null;
  }
  getAll(){
    debugger
    this.store.dispatch(ProductAction.getAllProducts({page: this.currentPage, size: this.pageSize}))
  }
  viewImages(images: ProductImage[]) {
    this.selectedImages = images;
  }
  editProduct(product: ProductResponse){}

  deleteProduct(){}
  onPageChange(page: number): void {
    this.currentPage = page;
    this.getAll();
  }

}
