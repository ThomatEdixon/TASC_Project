import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AuthenticateService } from '../../../../services/authentication.service';
import { Router } from '@angular/router';
import { UserResponse } from '../../../../models/user';
import { UserService } from '../../../../services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{

  credential: any;
  user!:UserResponse;
  constructor(private authService: AuthenticateService, private router:Router, private userService:UserService) {
    this.credential = this.authService.GetCredential
  }
  ngOnInit() {
  }

  IsAuthen = () => this.authService.LoggedIn
  ClickLogOut(){
    this.authService.Logout(this.authService.GetCredential?.data.token)
  }

}
