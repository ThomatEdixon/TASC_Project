import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, throwError } from "rxjs";
import { AuthenticateService } from "../authentication.service";

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authService: AuthenticateService) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let isLoggedIn = this.authService.LoggedIn;
    let currentUser = this.authService.GetCredential;
    if (isLoggedIn) {
      req = req.clone({ setHeaders: { Authorization: `Bearer ${currentUser?.data.token}` } });
    }
    return next.handle(req).pipe(catchError(err => {
      if (err.status === 401){
        if(this.authService.RefreshToken(currentUser?.data.token))
        this.authService.Logout(currentUser?.data.token)
      };
      return throwError(() => err);
    }));
  }
};
