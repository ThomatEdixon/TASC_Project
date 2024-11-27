import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { AuthenResponse } from '../models/authen';
import { Observable } from 'rxjs';
import { PaymentRequest } from '../models/payment';

const BaseUrl = environment.ApiUrl;
const Endpoint = 'payment';

@Injectable({
  providedIn: 'root'
})

export class PaymentService {

  constructor(private httpClient:HttpClient) { }
  createPayment(model: PaymentRequest) : Observable<AuthenResponse>{
    return this.httpClient.post<AuthenResponse>(`${BaseUrl}/${Endpoint}`,model);
  }
  getUrlCheckOut(paymentId:string) : Observable<AuthenResponse>{
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}/url-check-out/${paymentId}`);
  }
  getPaymentById(paymentId:string) : Observable<AuthenResponse>{
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}/${paymentId}`);
  }
  getStatusTransaction(paymentId:string) : Observable<AuthenResponse>{
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}/payment-success`);
  }
}
