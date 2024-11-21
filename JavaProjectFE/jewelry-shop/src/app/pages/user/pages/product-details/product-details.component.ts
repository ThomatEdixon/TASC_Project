import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router'; 
import { ProductService } from '../../../../services/product.service';
import { ProductResponse } from '../../../../models/product';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailComponent implements OnInit {
  product!: ProductResponse;
  productId!: string;
  quantity: number =1;

  constructor(
    private activatedRouter: ActivatedRoute, // Inject ActivatedRoute
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    this.activatedRouter.queryParams.subscribe(params => {
      this.productId = params['productId'];
    });
    this.getProductDetail(this.productId); 
  }

  getProductDetail(productId: string): void {
    this.productService.getProductById(productId).subscribe((res) => {
      if(res.errorCode == 200){
        this.product = res.data;
      }
    });
  }
  previousImage(){}
  nextImage(){}
  addToCart(){}
  buyNow(){}
  decreaseQuantity(){}
  increaseQuantity(){}
}

