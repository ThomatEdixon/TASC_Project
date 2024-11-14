import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenResponse } from '../models/authen';
const BaseUrl = environment.ApiUrl;
const Endpoint = 'material';
@Injectable({
  providedIn: 'root'
})
export class MaterialService {

  constructor(private httpClient: HttpClient, private router: Router) { }
  getAll():Observable<AuthenResponse> {
    return this.httpClient.get<AuthenResponse>(`${BaseUrl}/${Endpoint}`);
  }
}
