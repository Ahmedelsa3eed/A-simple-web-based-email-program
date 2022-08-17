import { Component } from '@angular/core';
import {AutoService} from "./service/auto/auto.service";

@Component({
  selector: 'cf-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'MailServer';
  username = ""
  constructor(public autoService: AutoService) { }

  logout() {
    this.autoService.logout();
  }
  refresh() {
    this.autoService.refresh();
  }
  getUserName(){
    return this.autoService.getUserName()
  }
}
