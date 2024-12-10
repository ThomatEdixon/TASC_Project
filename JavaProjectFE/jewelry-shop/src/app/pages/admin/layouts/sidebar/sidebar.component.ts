import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  constructor(private router:Router) { }
  isProductMenuOpen = false;
  isUserMenuOpen = false;
  isOrderMenuOpen = false
  ngOnInit() {
  }
  toggleProductMenu() {
    this.isProductMenuOpen = !this.isProductMenuOpen;
  }
  toggleUserMenu() {
    this.isUserMenuOpen = !this.isUserMenuOpen;
  }
  toggleOrderMenu() {
    this.isOrderMenuOpen = !this.isOrderMenuOpen;
  }

}
