import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-verify',
  templateUrl: './verify.component.html',
  styleUrls: ['./verify.component.css']
})
export class VerifyComponent implements OnInit {
  verifyForm: FormGroup;
  timeLeft: number = 60;
  interval: any;
  username:string ='';

  constructor(
    private fb: FormBuilder, 
    private activatedRouter: ActivatedRoute, 
    private userService:UserService,
    private router:Router
  ) {
    this.verifyForm = this.fb.group({
      verificationCode: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.activatedRouter.queryParams.subscribe(params => {
      this.username = params['username'];
    });
    this.startTimer();
  }

  startTimer() {
    this.interval = setInterval(() => {
      if (this.timeLeft > 0) {
        this.timeLeft--;
      } else {
        clearInterval(this.interval);
        // Xử lý khi hết thời gian (có thể gửi lại mã)
      }
    }, 1000);
  }

  onVerify() {
    this.verifyForm.markAllAsTouched();
    if (this.verifyForm.invalid) return
    let model = this.verifyForm.getRawValue();
    this.userService.Verify(this.username,model).pipe().subscribe((res)=>{
        if(res.code == 100){
          this.router.navigate(['change-password'],{queryParams: { username : this.username}})
        }
    })
  }
}

