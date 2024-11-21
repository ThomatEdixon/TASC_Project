import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CartService } from '../../../../services/cart.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  orderForm!: FormGroup;
  cartItems: any[] = []; // Placeholder for cart items
  totalPrice: number = 0; // Total amount placeholder
  couponDiscount: number = 0; // Discount placeholder

  constructor(private fb: FormBuilder,private cartService:CartService) {}

  ngOnInit(): void {
    this.initializeForm();
    this.loadCartItems(); 
    this.calculateTotalAmount(); 
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
    // Placeholder: Add logic to fetch cart items
    this.cartService.cartItems$.subscribe((items) => {
      this.cartItems = items;
    });
    this.cartService.totalPrice$.subscribe((price) => {
      this.totalPrice = price;
    });
  }

  calculateTotalAmount(): void {
    // Placeholder: Add logic to calculate the total amount
    console.log('Calculate total amount');
  }

  applyCoupon(): void {
    // Placeholder: Add logic to apply a coupon
    console.log('Apply coupon');
  }

  placeOrder(): void {
    if (this.orderForm.valid) {
      // Placeholder: Add logic to handle order placement
      console.log('Order placed:', this.orderForm.value);
    } else {
      console.log('Form is invalid');
    }
  }

  increaseQuantity(index: number): void {
    // Placeholder: Add logic to increase product quantity
    console.log(`Increase quantity for item at index: ${index}`);
  }

  decreaseQuantity(index: number): void {
    // Placeholder: Add logic to decrease product quantity
    console.log(`Decrease quantity for item at index: ${index}`);
  }

  confirmDelete(index: number): void {
    // Placeholder: Add logic to confirm and remove an item from the cart
    console.log(`Confirm delete for item at index: ${index}`);
  }
}
