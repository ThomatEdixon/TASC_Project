import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-unauthen',
  templateUrl: './unauthen.component.html',
  styleUrls: ['./unauthen.component.css']
})
export class UnauthenComponent implements OnInit {

  constructor(private router: Router, private location: Location) { }

  ngOnInit() {
    if (this.location.path() == '') {
      this.router.navigate(['login'])
    }
  }

}
