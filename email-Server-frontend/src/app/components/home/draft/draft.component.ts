import { Component, OnInit } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { Email } from 'src/app/models/Email';
import { User } from 'src/app/models/User';
import { GetEmailsService } from 'src/app/services/get-emails.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-draft',
  templateUrl: './draft.component.html',
  styleUrls: ['./draft.component.css']
})
export class DraftComponent implements OnInit {

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
    this.isLoading = true;
    this.setUser();
    this.emails = this.getEmails();
  }

  getEmails() {
    this.fetchEmails();
    return this.email$;
  }

  fetchEmails() {
    this.getEmailsService.requestEmails(this.user, 'draft')
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