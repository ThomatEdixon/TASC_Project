import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  changePasswordForm!: FormGroup;
  username:string ='';

  constructor(
    private fb: FormBuilder, 
    private router: Router,
    private activatedRouter:ActivatedRoute,
    private userService:UserService
  ) {
    
  }

  ngOnInit() {
    this.activatedRouter.queryParams.subscribe(params => {
      this.username = params['username'];
    });
    this.initForm()
  }

  initForm(){
    this.changePasswordForm = this.fb.group({
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
      confirmNewPassword: ['', [Validators.required]]
    });
  }
  onChangePassword() {
    this.changePasswordForm.markAllAsTouched();
    if (this.changePasswordForm.invalid) return
    let model = this.changePasswordForm.getRawValue();
    if(model.newPassword == model.confirmNewPassword){
      delete model.confirmNewPassword;
      this.userService.ChangePassword(this.username,model).pipe().subscribe((res)=>{
        if(res.code == 100){
          this.router.navigate(['login']);
        }
      });
    }else{
      console.log("password not macth");
    }
  }
}

