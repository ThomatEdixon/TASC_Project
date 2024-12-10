import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../../../../services/product.service';
import { ProductImage, ProductResponse } from '../../../../../models/product';
import { OrderService } from '../../../../../services/order.service';
import { OrderResponse } from '../../../../../models/order';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {

  products : ProductResponse[]=[];
  orders : OrderResponse[]=[];
  selectedImages: ProductImage[] | null = null;
  currentPage = 1;
  itemsPerPage = 9;
  visiblePages = [1, 2, 3];
  totalPage = Math.ceil(this.products.length / this.itemsPerPage);
  constructor(
    private productService:ProductService,
    private orderService:OrderService,
    private router:Router,
    private activatedRouter:ActivatedRoute
  ) { }
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
    this.orderService.getAll(this.currentPage, this.itemsPerPage).pipe().subscribe((res) => {
      this.orders = res.data.content;
      this.totalPage = res.data.totalPages; 
    });
  }

  updateVisiblePages() {
    this.visiblePages = Array.from({ length: this.totalPage }, (_, i) => i + 1)
      .slice(Math.max(this.currentPage - 1, 0), this.currentPage + 2);
  }
  editOrder(orderId: string){
    this.router.navigate(['edit'], { relativeTo: this.activatedRouter, queryParams: { orderId: orderId } });
  }

  deleteProduct(){}

}
