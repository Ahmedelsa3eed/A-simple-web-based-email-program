import { Component, OnInit } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { Email } from 'src/app/models/Email';
import { User } from 'src/app/models/User';
import { GetEmailsService } from 'src/app/services/get-emails.service';
import { RequestService } from 'src/app/services/request.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-emails-list',
  templateUrl: './emails-list.component.html',
  styleUrls: ['./emails-list.component.css']
})
export class EmailsListComponent implements OnInit {

  public user: User;
  public isLoading: boolean = false;
  public isRefuesdLogin: boolean = false;
  public emails?: Observable<Email[]>;
  public email$ = new BehaviorSubject<Email[]>([]);
  public searchString: string = "";
  public selectedValue: string = "";
  public folderName: string = "";

  constructor(private getEmailsService: GetEmailsService,
    private userService: UserService,
    private requestService: RequestService) {
    this.user = new User;
  }

  ngOnInit(): void {
    this.setUser();
  }

  getEmails($FileNameEvent: any) {
    console.log($FileNameEvent);
    this.resetFeedbackFlags();
    this.getEmailsService.requestEmails(this.user, $FileNameEvent)
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
    this.emails = this.email$;
  }

  updateEmailsList($event: any) {
    this.emails = $event;
  }

  search() {
    this.resetFeedbackFlags();
    this.requestService.search(this.user._id, this.searchString, this.folderName)
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

  sort(by: string) {
    this.resetFeedbackFlags();
    this.requestService.sort(by, this.folderName, this.user._id)
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

  private resetFeedbackFlags() {
    this.isLoading = true;
    this.isRefuesdLogin = false;
  }

}
