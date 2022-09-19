import { Component, OnInit } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {Email} from "../../../models/Email";
import {User} from "../../../models/User";
import {GetEmailsService} from "../../../services/get-emails.service";
import {UserService} from "../../../services/user.service";

@Component({
  selector: 'app-sent',
  templateUrl: './sent.component.html',
  styleUrls: ['./sent.component.css']
})
export class SentComponent implements OnInit {

  public user: User;
  public isLoading: boolean = false;
  public isRefuesdLogin: boolean = false;
  emails?: Observable<Email[]>;
  email$ = new BehaviorSubject<Email[]>([]);

  constructor(private getEmailsService:GetEmailsService,
    private userService:UserService) {
    this.user = new User;
  }

  ngOnInit(): void {
    this.setUser();
    this.emails = this.getSentEmails();
  }

  getSentEmails() {
    this.fetchSentEmails();
    return this.email$;
  }

  fetchSentEmails() {
    this.isLoading = true;
    this.getEmailsService.requestEmails(this.user, 'sent')
    .subscribe({
      next: (res) => {
        console.log(res);
        this.isLoading = false;
        // @ts-ignore
        this.email$.next(res.body);
      },
      error: (e) => {
        this.isLoading = false;
        this.isRefuesdLogin = true;
        console.error(e);
      },
      complete: () => console.info('complete')
    })
  }

  private setUser() {
    this.userService.getUser().subscribe(res => {
      this.user = res;
      console.log(res);
    });
  }

}
