import { LocalStorageWrapper } from './../../services/localStorageWrapper.service';
import { Component, OnInit } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { Email } from 'src/app/models/Email';
import { User } from 'src/app/models/User';
import { GetEmailsService } from 'src/app/services/get-emails.service';
import { RequestService } from 'src/app/services/request.service';
import { Router } from "@angular/router";

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
    private userService: LocalStorageWrapper,
    private requestService: RequestService,
    private router: Router) {
    this.user = new User;
  }

  ngOnInit(): void {
    this.setUser();
    this.getEmails('inbox');
  }

  private setUser() {
    this.user = this.userService.getUser();
  }

  getEmails($FileNameEvent: any) {
    console.log(`get emails from ${$FileNameEvent}`);
    this.folderName = $FileNameEvent;
    this.resetFeedbackFlags();
    this.getEmailsService.requestEmails(this.user, $FileNameEvent)
    .subscribe({
      next: (res) => {
        this.isLoading = false;
        // @ts-ignore
        this.email$.next(res.body);
      },
      error: (e) => {
        this.isLoading = false;
        this.isRefuesdLogin = true;
        console.error(e);
      },
      complete: () => console.info('Emails fetched successfully!')
    })
    this.emails = this.email$;
  }

  private resetFeedbackFlags() {
    this.isLoading = true;
    this.isRefuesdLogin = false;
  }
  
  updateEmailsList($event: any) {
    console.log(`update ${$event} email list`);
    this.getEmails($event);
  }

  search() {
    this.resetFeedbackFlags();
    this.folderName = this.router.url.split('/')[3];
    this.folderName = this.folderName.charAt(0).toUpperCase() + this.folderName.slice(1);
    console.log(this.folderName);
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
    this.folderName = this.router.url.split('/')[3];
    this.folderName = this.folderName.charAt(0).toUpperCase() + this.folderName.slice(1);
    console.log(this.folderName);
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



}
