import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenResponse} from '../models/authen';
import { environmentProduct } from '../../environments/product/environment.product.development';
const BaseUrl = environmentProduct.ApiUrl;
const Endpoint = 'product';
const EndpointUpload = 'upload';
@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient, private router: Router) {
    
  }
  getFeaturedProducts(): Observable<AuthenResponse> {
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}?page=1&size=10`);
  }
  getImage(imageName: string) {
    return this.httpClient.get(`${BaseUrl}/${EndpointUpload}/${imageName}`, { responseType: 'blob' });
  }
  getProducts(page:number,size:number): Observable<AuthenResponse> {
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}?page=${page}&size=${size}`);
  }
}
