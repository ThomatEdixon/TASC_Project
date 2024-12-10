import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../../../../services/product.service';
import { ProductImage, ProductResponse } from '../../../../../models/product';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  constructor(
      private productService:ProductService,
      private router:Router,
      private activatedRouter:ActivatedRoute
    ) { }

  products : ProductResponse[]=[];
  selectedImages: ProductImage[] | null = null;
  currentPage = 1;
  itemsPerPage = 9;
  visiblePages = [1, 2, 3];
  totalPage = Math.ceil(this.products.length / this.itemsPerPage);

  ngOnInit() {
    this.getAll();
  }
  closeModal() {
    this.selectedImages = null;
  }
  onChangePage(page: number) {
    this.currentPage = page;
    this.getAll();
  }

  getAll() {
    this.productService.getProducts(this.currentPage, this.itemsPerPage).pipe().subscribe((res) => {
      this.products = res.data.content;
      this.totalPage = res.data.totalPages; 
    });
  }

  updateVisiblePages() {
    this.visiblePages = Array.from({ length: this.totalPage }, (_, i) => i + 1)
      .slice(Math.max(this.currentPage - 1, 0), this.currentPage + 2);
  }
  editProduct(productId: string){
    this.router.navigate(['edit'], { relativeTo: this.activatedRouter, queryParams: { productId: productId } });
  }

  deleteProduct(){}

}
