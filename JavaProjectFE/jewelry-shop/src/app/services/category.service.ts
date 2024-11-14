import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthenResponse } from '../models/authen';
import { Observable } from 'rxjs/internal/Observable';
const BaseUrl = environment.ApiUrl;
const Endpoint = 'category';
@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private httpClient: HttpClient, private router: Router) { }
  getAll(): Observable<AuthenResponse> {
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}`);
  }
}
