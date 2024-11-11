import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticateService } from '../../../../services/authentication.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent{

  credential: any
  constructor(private authService: AuthenticateService) {
    this.credential = this.authService.GetCredential
  }

  ClickLogOut(){
    this.authService.Logout(this.authService.GetCredential?.data.token)
  }

}
