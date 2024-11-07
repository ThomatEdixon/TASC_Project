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
  credentialSubject: BehaviorSubject<AuthenResponse | null>;
  constructor(private httpClient: HttpClient, private router: Router) {
    this.credentialSubject = new BehaviorSubject<AuthenResponse | null>(JSON.parse(localStorage.getItem('credential') || '{}'));
  }

  get LoggedIn(): boolean { return !(JSON.stringify(this.credentialSubject.value) === '{}') }
  get GetCredential() { return this.credentialSubject.value; }

  Login(model: AuthenRequest): Observable<AuthenResponse>{
    return this.httpClient.post<AuthenResponse>(`${BaseUrl}/${Endpoint}/login`, model);
  }

  Logout(model:AuthenToken): Observable<AuthenResponse> {
    localStorage.removeItem('credential');
    this.credentialSubject.next(JSON.parse('{}'));
    this.router.navigateByUrl('/login');
    return this.httpClient.post<AuthenResponse>(`${BaseUrl}/${Endpoint}/logout`, model);
  }
  Introspect(model:any): Observable<AuthenResponse> {
    return this.httpClient.post<AuthenResponse>(`${BaseUrl}/${Endpoint}/introspect`, model);
  }
  RefreshToken(model:AuthenToken):Observable<AuthenResponse>{
    return this.httpClient.post<AuthenResponse>(`${BaseUrl}/${Endpoint}/refresh`, model);
  }
}
