import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UserRequest } from '../models/user';
import { AuthenResponse } from '../models/authen';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { th } from 'date-fns/locale';

const BaseUrl = environment.ApiUrl;
const Endpoint = 'user';
@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient, private router: Router) {
  }
  Register(model:UserRequest):Observable<AuthenResponse>{
    return this.httpClient.post<AuthenResponse>(`${BaseUrl}/${Endpoint}`, model);
  }
  GetInfo():Observable<AuthenResponse>{
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}/my-info`);
  }
  GetAll():Observable<AuthenResponse>{
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}`);
  }
  GetUserById(userId:number):Observable<AuthenResponse>{
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}/${userId}`);
  }
  Update(userId:number,model:UserRequest):Observable<AuthenResponse>{
    return this.httpClient.put<AuthenResponse>(`${BaseUrl}/${Endpoint}/${userId}`,model);
  }
  Delete(userId:number):Observable<AuthenResponse>{
    return this.httpClient.delete<AuthenResponse>(`${BaseUrl}/${Endpoint}/${userId}`);
  }
}
