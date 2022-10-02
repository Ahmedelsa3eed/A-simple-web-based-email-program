import { LocalStorageWrapper } from './../../services/localStorageWrapper.service';
import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isSignedIn: boolean = false;

  constructor(private localStorageWrapper: LocalStorageWrapper,
    public router: Router) { }

  ngOnInit(): void {
  }

  logout(): void {
    this.localStorageWrapper.clear();
    this.isSignedIn = false;
  }

}
