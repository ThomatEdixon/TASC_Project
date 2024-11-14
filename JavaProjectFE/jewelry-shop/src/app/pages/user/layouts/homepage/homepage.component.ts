import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ProductService } from '../../../../services/product.service';
import { ProductResponse } from '../../../../models/product';
import { Observable } from 'rxjs';
import { isThisMonth } from 'date-fns';

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
  constructor(private productService: ProductService, private sanitizer: DomSanitizer) { }

  ngOnInit() {
    this.getFeaturedProduct()
  }
  getFeaturedProduct() {
    this.productService.getFeaturedProducts().subscribe((res) => {
      if (res.code === 100) {
        this.products = Object.values(res.data.content);
        
        this.products.forEach(product => {
          product.productImages[0].imageUrl; 
        });
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

}
