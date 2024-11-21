import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private cartItemsSubject = new BehaviorSubject<any[]>(this.getCartFromLocalStorage());
  cartItems$ = this.cartItemsSubject.asObservable();

  private totalPriceSubject = new BehaviorSubject<number>(this.calculateTotalPrice());
  totalPrice$ = this.totalPriceSubject.asObservable();

  private distinctProductCountSubject = new BehaviorSubject<number>(this.getCartFromLocalStorage().length);
  distinctProductCount$ = this.distinctProductCountSubject.asObservable();

  constructor() {}

  private getCartFromLocalStorage(): any[] {
    return JSON.parse(localStorage.getItem('cart') || '[]');
  }

  private updateCartInLocalStorage(cart: any[]): void {
    localStorage.setItem('cart', JSON.stringify(cart));
    this.cartItemsSubject.next(cart);
    this.totalPriceSubject.next(this.calculateTotalPrice());
    this.distinctProductCountSubject.next(cart.length);
  }

  addToCart(product: any): void {
    const cart = this.getCartFromLocalStorage();
    const existingItem = cart.find((item) => item.productId === product.productId);

    if (existingItem) {
      existingItem.quantity += 1;
    } else {
      cart.push({ ...product, quantity: 1 });
    }

    this.updateCartInLocalStorage(cart);
  }

  removeFromCart(productId: number): void {
    const cart = this.getCartFromLocalStorage().filter((item) => item.productId !== productId);
    this.updateCartInLocalStorage(cart);
  }

  updateQuantity(productId: number, quantity: number): void {
    const cart = this.getCartFromLocalStorage();
    const item = cart.find((item) => item.productId === productId);

    if (item) {
      item.quantity = quantity;
      if (item.quantity <= 0) {
        this.removeFromCart(productId);
      } else {
        this.updateCartInLocalStorage(cart);
      }
    }
  }

  private calculateTotalPrice(): number {
    const cart = this.getCartFromLocalStorage();
    return cart.reduce((total, item) => total + item.price * item.quantity, 0);
  }
}
