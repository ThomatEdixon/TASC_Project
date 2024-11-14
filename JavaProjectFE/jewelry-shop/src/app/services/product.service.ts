import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenResponse} from '../models/authen';
import { environment } from '../../environments/environment';
import { Material, ProductImage, ProductRequest } from '../models/product';
const BaseUrl = environment.ApiUrl;
const Endpoint = 'product';
const EndpointUpload = 'upload';
@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient, private router: Router) {
    
  }
  getProductById(id:String):Observable<AuthenResponse>{
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}/${id}`);
  }
  getFeaturedProducts(): Observable<AuthenResponse> {
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}/public/getAll?page=1&size=10`);
  }
  getProducts(page:number,size:number): Observable<AuthenResponse> {
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}`+"/public/getAll"+`?page=${page}&size=${size}`);
  }
  createProduct(model:ProductRequest):Observable<AuthenResponse>{
    return this.httpClient.post<AuthenResponse>(`${BaseUrl}/${Endpoint}`,model);
  }
  updateProduct(id:String,model:ProductRequest):Observable<AuthenResponse>{
    return this.httpClient.put<AuthenResponse>(`${BaseUrl}/${Endpoint}/${id}`,model)
  }
  addProductImage(productId: number, files: File[]): Observable<AuthenResponse> {
    const formData = new FormData();
    // Duyệt qua tất cả các file và thêm vào FormData
    files.forEach((file, index) => {
      formData.append('files', file); // Sử dụng 'files' theo đúng yêu cầu của BE
    });
  
    return this.httpClient.post<AuthenResponse>(`${BaseUrl}/${EndpointUpload}/${productId}`, formData, {
      headers: {
      }
    });
  }
  updateProductImage(imageId: String, files: File[]): Observable<AuthenResponse> {
    const formData = new FormData();
    files.forEach((file, index) => {
      formData.append('files', file); 
    });
    return this.httpClient.put<AuthenResponse>(`${BaseUrl}/${EndpointUpload}/${imageId}`, formData, {
      headers: {
      }
    });
  }
  addMaterial(productId:number,model:Material):Observable<AuthenResponse>{
    return this.httpClient.post<AuthenResponse>(`${BaseUrl}/${Endpoint}/${productId}`,model);
  } 
}
