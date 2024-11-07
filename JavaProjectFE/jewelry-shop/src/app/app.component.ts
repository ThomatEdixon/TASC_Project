import { Component } from '@angular/core';
import { AuthenticateService } from './services/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  constructor(private authService: AuthenticateService,private router:Router) { }
  ngOnInit(){
    this.router.navigateByUrl('home');
  }

}
