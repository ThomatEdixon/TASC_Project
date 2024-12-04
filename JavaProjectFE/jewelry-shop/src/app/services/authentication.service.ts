import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, lastValueFrom, Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { AuthenRequest, AuthenResponse, AuthenToken} from '../models/authen';
const BaseUrl = environment.ApiUrl;
const Endpoint = 'authentication';
@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {
  credentialSubject: BehaviorSubject<string | null>;
  constructor(private httpClient: HttpClient, private router: Router) {
    this.credentialSubject = new BehaviorSubject<string | null>(JSON.parse(localStorage.getItem('token') || '{}'));
  }

  get LoggedIn(): boolean { return !(JSON.stringify(this.credentialSubject.value) === '{}') }
  get GetCredential() { return this.credentialSubject.value; }
  get IsAdmin():boolean{ return false}

  Login(model: AuthenRequest): Observable<AuthenResponse>{
    return this.httpClient.post<AuthenResponse>(`${BaseUrl}/${Endpoint}/login`, model);
  }

  Logout(model:any): Observable<AuthenResponse> {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('role');
    this.credentialSubject.next(JSON.parse('{}'));
    this.router.navigateByUrl('/login');
    return this.httpClient.post<AuthenResponse>(`${BaseUrl}/${Endpoint}/logout`, model);
  }
  Introspect(model:any): Observable<AuthenResponse> {
    return this.httpClient.post<AuthenResponse>(`${BaseUrl}/${Endpoint}/introspect`, model);
  }
  RefreshToken(model:any):Observable<AuthenResponse>{
    return this.httpClient.post<AuthenResponse>(`${BaseUrl}/${Endpoint}/refresh`, model);
  }
}
