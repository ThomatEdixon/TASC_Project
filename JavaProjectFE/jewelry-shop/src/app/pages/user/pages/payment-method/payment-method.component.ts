import { Component, OnInit } from '@angular/core';
import { CartService } from '../../../../services/cart.service';
import { PaymentService } from '../../../../services/payment.service';
import { PaymentRequest } from '../../../../models/payment';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-payment-method',
  templateUrl: './payment-method.component.html',
  styleUrls: ['./payment-method.component.css']
})
export class PaymentMethodComponent implements OnInit {
  cartItems = JSON.parse(localStorage.getItem('cart') || '[]');
  totalPrice !: number;
  paymentMethods = [
    { name: 'PayOS', value: 'PayOS', icon: 'https://smadeandsmight.com/wp-content/uploads/2022/04/full-lc-logo-color.png' },
    { name: 'PayPal', value: 'PayPal', icon: 'https://cellphones.com.vn/sforum/wp-content/uploads/2021/06/cach-tao-tai-khoan-paypal-1.jpg' },
    { name: 'VNPay', value: 'VNPay', icon: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTp1v7T287-ikP1m7dEUbs2n1SbbLEqkMd1ZA&s' },
  ];
  payment : PaymentRequest = {
    orderId : JSON.parse(localStorage.getItem('orderId') || '[]'),
    paymentMethod : ""
  }

  constructor(private cartService:CartService, private paymentService:PaymentService) {}

  ngOnInit(): void {}
  loadCartItems(): void {
    this.cartService.cartItems$.subscribe((items) => {
      this.cartItems = items;
    });
    this.cartService.totalPrice$.subscribe((price) => {
      this.totalPrice = price;
    });
  }
  selectPaymentMethod(method: string): void {
    this.payment.paymentMethod = method;
  }
  async confirmPaymentMethod() {
    try {
      // Loại bỏ dấu ngoặc kép trong orderId
      this.payment.orderId = this.payment.orderId.replace(/^"|"$/g, '');
      const createPaymentResponse = await this.paymentService.createPayment(this.payment).toPromise();
      // console.log(createPaymentResponse)
      if (!createPaymentResponse) {
        console.error('Create Payment response is undefined.');
        return;
      }
      const checkoutResponse = await this.paymentService.getUrlCheckOut(createPaymentResponse.data.paymentId).toPromise();
      console.log(checkoutResponse);
      window.location.href = checkoutResponse?.data.checkoutUrl;
      if (!checkoutResponse) {
        console.error('Checkout response is undefined.');
        return;
      }
    } catch (error: any) {
      if (error instanceof HttpErrorResponse && error.status === 400) {
        console.error('Bad Request:', error.error);
      } else {
        console.error('An error occurred:', error.message);
      }
    }
  }
  
}
