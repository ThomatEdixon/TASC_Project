import { Component, OnInit } from '@angular/core';
import { AuthenticateService } from '../../../services/authentication.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

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

  OnClickLogin() {
    this.form.markAllAsTouched();
    if (this.form.invalid) return;
    const model = this.form.getRawValue();
    this.authService.Login(model).subscribe({
      next: (res) => {
        if (res.errorCode === 200) {
          this.authService.credentialSubject.next(res);
          localStorage.setItem('credential', JSON.stringify(res));
          this.router.navigate(['user']);
        } else {
          console.log('Authenticate fail: ' + res.message);
        }
      },
      error: (error: HttpErrorResponse) => {
        if (error.status === 400) {
          this.handleBackendErrors(error.error);
        }
      }
    });
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

