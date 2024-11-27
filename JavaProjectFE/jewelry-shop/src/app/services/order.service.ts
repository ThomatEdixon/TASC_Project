import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { AuthenResponse } from '../models/authen';
import { OrderRequest } from '../models/order';
const BaseUrl = environment.ApiUrl;
const Endpoint = 'order';
@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private httpClient: HttpClient,) { 

  }
  createOrder(model: OrderRequest) : Observable<AuthenResponse>{
    return this.httpClient.post<AuthenResponse>(`${BaseUrl}/${Endpoint}`,model);
  }
  confirmPaymentMethod(orderId:string,paymentMethod:string) : Observable<AuthenResponse>{
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}/payment-method/${orderId}?paymentMethod=${paymentMethod}`);
  }
  getOrderById(orderId:string) : Observable<AuthenResponse>{
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}/`+ orderId);
  }
}
