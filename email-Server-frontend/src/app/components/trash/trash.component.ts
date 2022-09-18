import { Component, OnInit } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {Email} from "../../models/Email";
import {User} from "../../models/User";
import {GetEmailsService} from "../../services/get-emails.service";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-trash',
  templateUrl: './trash.component.html',
  styleUrls: ['./trash.component.css']
})
export class TrashComponent implements OnInit {

  emails?: Observable<Email[]>;
  email$ = new BehaviorSubject<Email[]>([]);
  public user: User;

  constructor(private getEmailsService: GetEmailsService, 
    private userService:UserService) {
    this.user = new User;
  }

  ngOnInit(): void {
    this.setUser();
    this.emails = this.getInboxEmails();
  }

  getInboxEmails():Observable<Email[]> {
    this.getEmailsService.requestEmails(this.user, 'trash')
    .subscribe(res => {
      console.log(res);

      // @ts-ignore
      this.email$.next(res.body);
    });
    return this.email$;
  }
  
  private setUser() {
    this.userService.getUser().subscribe(res => {
      this.user = res;
      console.log(res);
    });
  }

}
