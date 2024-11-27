import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CartService } from '../../../../services/cart.service';
import { OrderService } from '../../../../services/order.service';
import { OrderDetailRequest, OrderRequest } from '../../../../models/order';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  orderForm!: FormGroup;
  cartItems: any[] = [];
  totalPrice: number = 0;
  order: OrderRequest = {
    userId: null,
    orderDetails: [],
    orderDate: new Date().toISOString(), 
    status: 'PENDING', 
  }
  userId!: string | null;

  constructor(
    private fb: FormBuilder,
    private cartService: CartService,
    private orderService: OrderService,
    private router:Router
  ) {  }

  ngOnInit(): void {
    this.initializeForm();
    this.loadCartItems();
  }

  initializeForm(): void {
    this.orderForm = this.fb.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      phone_number: ['', [Validators.required, Validators.minLength(6)]],
      address: ['', [Validators.required, Validators.minLength(5)]],
      note: [''],
      shipping_method: ['express', Validators.required],
      payment_method: ['cod', Validators.required],
      couponCode: ['']
    });
  }

  loadCartItems(): void {
    this.cartService.cartItems$.subscribe((items) => {
      this.cartItems = items;
    });
    this.cartService.totalPrice$.subscribe((price) => {
      this.totalPrice = price;
    });
  }

  placeOrder(): void {
    this.orderForm.markAllAsTouched();
    if (this.orderForm.invalid) return;
  
    const model = this.orderForm.getRawValue();
    if (this.orderForm.valid) {
      debugger
      this.order.userId= localStorage.getItem('userId'); 
      this.order.userId= this.order.userId.replace(/^"|"$/g, '');
      this.order.orderDetails = this.cartItems.map(item => {
        const orderDetail: OrderDetailRequest = {
          orderId:'',
          productId: item.productId,
          quantity: item.quantity,
          pricePerUnit: item.price,
          totalPrice: item.quantity * item.price, 
        };
        return orderDetail;
      });
      this.orderService.createOrder(this.order).pipe().subscribe((res)=>{
        localStorage.removeItem('orderId');
        localStorage.setItem('orderId', JSON.stringify(res.data.orderId) );
      });
      console.log(model.payment_method);
      
      if(model.payment_method === 'cod'){
        
      }else{
        console.log(this.order)
        this.router.navigateByUrl('/user/payment-method');
      }
    } else {
      console.log('Form is invalid');
    }
  }

  increaseQuantity(index: number): void {
    console.log(`Increase quantity for item at index: ${index}`);
  }

  decreaseQuantity(index: number): void {
    console.log(`Decrease quantity for item at index: ${index}`);
  }

  confirmDelete(index: number): void {
    console.log(`Confirm delete for item at index: ${index}`);
  }
}
