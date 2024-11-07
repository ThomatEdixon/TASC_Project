import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AuthenticateService } from '../../services/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  credential: any
  constructor(private authService: AuthenticateService) {
    this.credential = this.authService.GetCredential
  }

  IsAuthen = () => this.authService.LoggedIn

  ClickLogOut(){
    this.authService.Logout(this.authService.GetCredential?.data.token)
  }

}
