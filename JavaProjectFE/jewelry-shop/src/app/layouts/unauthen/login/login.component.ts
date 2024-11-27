import { Component, OnInit } from '@angular/core';
import { AuthenticateService } from '../../../services/authentication.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form!: FormGroup;
  backendErrors: { [key: string]: string } = {}; 

  constructor(
    private authService: AuthenticateService,
    private userService: UserService,
    private fb: FormBuilder, 
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.form = this.fb.group({
      username: [null, Validators.required],
      password: [null, Validators.required]
    });
  }

  async OnClickLogin() {
    this.form.markAllAsTouched();
    if (this.form.invalid) return;
  
    const model = this.form.getRawValue();
  
    try {
      // Gọi API Login
      const loginResponse = await this.authService.Login(model).toPromise();
  
      if (!loginResponse) {
        console.error('Login response is undefined.');
        return;
      }
  
      if (loginResponse.errorCode === 200) {
        // Lưu thông tin token
        this.authService.credentialSubject.next(loginResponse.data.token);
        localStorage.setItem('token', JSON.stringify(loginResponse.data.token));
  
        // Gọi API GetInfo sau khi Login thành công
        const userInfo = await this.userService.GetInfo(loginResponse.data.token).toPromise();
        if (userInfo) {
          localStorage.setItem('userId', JSON.stringify(userInfo.data.userId));
          localStorage.setItem('role', JSON.stringify(userInfo.data.role.name));
        }
  
        // Điều hướng sau khi hoàn thành
        this.router.navigate(['user']);
      } else {
        console.log('Authenticate fail: ' + loginResponse.message);
      }
    } catch (error: any) {
      if (error instanceof HttpErrorResponse && error.status === 400) {
        this.handleBackendErrors(error.error);
      } else {
        console.error('An error occurred:', error.message);
      }
    }
  }
  
  handleBackendErrors(errors: any) {
    this.backendErrors = {}; // Reset lỗi cũ
    if (errors && typeof errors === 'object') {
      Object.keys(errors).forEach((key) => {
        const control = this.form.get(key);
        if (control) {
          control.setErrors({ backend: errors[key] });
        } else {
          this.backendErrors[key] = errors[key]; // Lưu lỗi chung (nếu có)
        }
      });
    }
  }
  
}

