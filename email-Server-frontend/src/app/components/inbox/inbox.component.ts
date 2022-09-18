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
  public isLoading: boolean = false;
  public isRefuesdLogin: boolean = false;
  emails?: Observable<Email[]>;
  email$ = new BehaviorSubject<Email[]>([]);

  constructor(private getEmailsService: GetEmailsService,
    private userService: UserService) {
    this.user = new User;
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.setUser();
    this.emails = this.getInboxEmails();
  }

  getInboxEmails() {
    this.fetchInboxEmails();
    return this.email$;
  }

  fetchInboxEmails() {
    this.getEmailsService.requestEmails(this.user, 'inbox')
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
