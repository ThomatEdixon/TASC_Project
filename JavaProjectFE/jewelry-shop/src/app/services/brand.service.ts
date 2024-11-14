import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenResponse } from '../models/authen';
const BaseUrl = environment.ApiUrl;
const Endpoint = 'brand';
@Injectable({
  providedIn: 'root'
})

export class BrandService {

  constructor(private httpClient: HttpClient, private router: Router) { }
  getAll(): Observable<AuthenResponse> {
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}`);
  }
}
