import { HeaderService } from './../../services/header.service';
import { LocalStorageWrapper } from './../../services/localStorageWrapper.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isSignedIn: boolean = false;

  constructor(private localStorageWrapper: LocalStorageWrapper,
    private headerService: HeaderService) { }

  ngOnInit(): void {
    this.isSignedIn = this.localStorageWrapper.isUserDefined();
    console.log(this.isSignedIn);
    if (this.isSignedIn)
      return;
    this.headerService.getSignedInResults()
    .subscribe((res) => this.isSignedIn = res);
    console.log(this.isSignedIn);
  }

  logout(): void {
    this.localStorageWrapper.clear();
    this.isSignedIn = false;
  }

}
