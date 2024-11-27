import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ProductService } from '../../../../services/product.service';
import { ProductResponse } from '../../../../models/product';
import { Observable } from 'rxjs';
import { isThisMonth } from 'date-fns';
import { ActivatedRoute, Router } from '@angular/router';
import { CartService } from '../../../../services/cart.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  products: ProductResponse[] = [];
  imageUrl: SafeUrl | null = null;
  currentIndex = 0;
  itemsPerPage = 4;
  constructor(
    private productService: ProductService, 
    private router: Router,
    private activatedRouter: ActivatedRoute,
    private cartService:CartService
  ) { }

  ngOnInit() {
    this.productService.getFeaturedProducts().subscribe((res) => {
      if (res.errorCode === 200) {
        this.products = Object.values(res.data.content);
        console.log(this.products);
      }
    });
  }
  // Hàm trượt tới
  slideNext() {
    const maxIndex = Math.floor(this.products.length / this.itemsPerPage) - 1;
    if (this.currentIndex < maxIndex) {
      this.currentIndex++;
    } else {
      this.currentIndex = 0; // Quay lại đầu
    }
    this.updateSliderPosition();
  }

  // Hàm trượt lui
  slidePrev() {
    const maxIndex = Math.floor(this.products.length / this.itemsPerPage) - 1;
    if (this.currentIndex > 0) {
      this.currentIndex--;
    } else {
      this.currentIndex = maxIndex; // Quay lại cuối
    }
    this.updateSliderPosition();
  }

  // Hàm cập nhật vị trí slider
  updateSliderPosition() {
    const sliderTrack = document.querySelector('.slider-track') as HTMLElement;
    const translateX = -this.currentIndex * 100;
    sliderTrack.style.transform = `translateX(${translateX}%)`;
  }
  onDetailProduct(productId: string) {
    this.router.navigate(['user/product-detail'], { queryParams: { productId: productId } });
  }


  addToCart(product: ProductResponse): void {
    this.cartService.addToCart(product); // Gọi service để thêm vào cart
  }


}
