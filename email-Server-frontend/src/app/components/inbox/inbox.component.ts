import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import {Email} from "../../models/Email";
import {GetEmailsService} from "../../services/get-emails.service";
import {User} from "../../models/User";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css']
})
export class InboxComponent implements OnInit {

  public user: User;
  emails?: Observable<Email[]>;
  email$ = new BehaviorSubject<Email[]>([]);

  constructor(private getEmailsService: GetEmailsService,
    private userService: UserService) {
    this.user = new User;
  }

  ngOnInit(): void {
    this.setUser();
    this.emails = this.getInboxEmails();
  }

  getInboxEmails(): Observable<Email[]> {
    this.getEmailsService.requestEmails(this.user, 'inbox')
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
