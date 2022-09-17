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
  emails?:Observable<Email[]>;
  email$ = new BehaviorSubject<Email[]>([]);
  public user:User;

  constructor(private getEmailsService:GetEmailsService, private userService:UserService) {
    this.emails = this.getInboxEmails();
    this.user = new User;
  }

  ngOnInit(): void {

  }

  getInboxEmails():Observable<Email[]> {
    this.userService.getUser().subscribe(res => {
      this.user = res;
      console.log(res);
    });
    this.getEmailsService.requestEmails(this.user, 'inbox').subscribe(
      res => {
        console.log(res);

        // @ts-ignore
        this.email$.next(res.body);
      });

      return this.email$;

    }


}
