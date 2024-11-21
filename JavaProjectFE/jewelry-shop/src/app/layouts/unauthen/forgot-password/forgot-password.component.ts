import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  constructor(private userService: UserService,
    private fb: FormBuilder, 
    private router: Router
   ) { }

  forgotForm!: FormGroup;

  ngOnInit() {
    this.initForm()
  }

  initForm() {
    this.forgotForm = this.fb.group({
      username: [null, Validators.required],
      email: [null, Validators.required]
    })
  }

  OnClickForgotPassword(){
    this.forgotForm.markAllAsTouched();
    if (this.forgotForm.invalid) return
    let model = this.forgotForm.getRawValue();
    console.log(model);
    this.userService.ForgotPassWord(model).pipe().subscribe((res)=>{
      if(res.errorCode == 200){
        this.router.navigate(['verify'],{queryParams: { username : model.username}});
      }
    });
    
  }

}
