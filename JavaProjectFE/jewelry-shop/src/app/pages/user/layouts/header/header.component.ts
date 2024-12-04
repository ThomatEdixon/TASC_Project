import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AuthenticateService } from '../../../../services/authentication.service';
import { Router } from '@angular/router';
import { UserResponse } from '../../../../models/user';
import { UserService } from '../../../../services/user.service';
import { CartService } from '../../../../services/cart.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{

  token: any;
  user!:UserResponse;
  cartItems: any[] = [];
  cartCount: number = 0;
  totalPrice: number = 0;
  constructor(
    private authService: AuthenticateService, 
    private router:Router, 
    private userService:UserService,
    private cartService:CartService
  ) {
    this.token = this.authService.GetCredential
  }
  ngOnInit() {
    this.loadCart();
  }

  IsAdmin():boolean{
    let role = localStorage.getItem('role');
    if(role && JSON.parse(role) === 'ADMIN'){
      return true;
    }else{
      return false;
    }
  }
  IsAuthen():boolean{
    let token = localStorage.getItem('token') ;
    if(token){
      return true;
    }
    return false;
  }
  ClickLogOut(){
    this.authService.Logout(this.authService.GetCredential)
  }

  loadCart(): void {
    this.cartService.cartItems$.subscribe((items) => {
      this.cartItems = items;
      this.cartCount = items.reduce((count, item) => count + item.quantity, 0);
    });
    this.cartService.distinctProductCount$.subscribe((count) => {
      this.cartCount = count;
    });
    this.cartService.totalPrice$.subscribe((price) => {
      this.totalPrice = price;
    });
  }

  removeFromCart(item: any): void {
    this.cartService.removeFromCart(item.productId);
  }

  updateCart(): void {
    localStorage.setItem('cart', JSON.stringify(this.cartItems)); 
    this.cartCount = this.cartItems.length; 
  }

  // Tính tổng giá tiền
  getTotalPrice() {
    this.cartService.totalPrice$.subscribe((price) => {
      this.totalPrice = price;
    }); 
  }

  // Điều hướng đến trang thanh toán

}
