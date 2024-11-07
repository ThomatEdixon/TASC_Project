import { Component, OnInit } from '@angular/core';
import { AuthenticateService } from '../../../services/authentication.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserRequest } from '../../../models/user';
import { UserService } from '../../../services/user.service';
import { format } from 'date-fns';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private userService: UserService, private fb: FormBuilder, private router: Router) { }
  registerForm!: FormGroup;

  ngOnInit(): void {
    this.initForm()
  }

  initForm() {
    this.registerForm = this.fb.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
      confirmPassword: [null, Validators.required],
      email: [null, Validators.required],
      phoneNumber: [null, Validators.required],
      dob: [null, Validators.required],
      address: [null, Validators.required]
    });
  }
  OnClickRegister(){
    debugger
    this.registerForm.markAllAsTouched();
    if (this.registerForm.invalid) return
    let model = this.registerForm.getRawValue();
    delete model.confirmPassword;
    if (model.dob) {
      model.dob = format(new Date(model.dob), "yyyy-MM-dd'T'HH:mm:ss");
    }
    console.log(model);
    this.userService.Register(model).pipe().subscribe((res)=>{
      if(res.code == 100){
        this.router.navigateByUrl('/login');
      }
    });
  }

}
