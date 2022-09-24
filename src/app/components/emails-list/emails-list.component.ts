import { LocalStorageWrapper } from './../../services/localStorageWrapper.service';
import { Component, OnInit } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { Email } from 'src/app/models/Email';
import { User } from 'src/app/models/User';
import { GetEmailsService } from 'src/app/services/get-emails.service';
import { RequestService } from 'src/app/services/request.service';
import { HttpResponse } from '@angular/common/http';

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
    private requestService: RequestService) {
      this.user = new User;
  }

  ngOnInit(): void {
    this.user = this.userService.getUser();
    this.getEmails('inbox');
  }

  getEmails($FileNameEvent: any) {
    this.folderName = $FileNameEvent;
    this.resetFeedbackFlags();
    this.getEmailsService.requestEmails(this.user, $FileNameEvent)
    .subscribe({
      next: (res) => this.handleRespone(res),
      error: (e) => this.handleResponseError(e),
      complete: () => console.info('Emails fetched successfully!')
    })
    this.emails = this.email$;
  }

  private resetFeedbackFlags() {
    this.isLoading = true;
    this.isRefuesdLogin = false;
  }

  private handleRespone(res: HttpResponse<Email[]>) {
    this.isLoading = false;
    // @ts-ignore
    this.email$.next(res.body);
  }

  private handleResponseError(err: any) {
    this.isLoading = false;
    this.isRefuesdLogin = true;
    console.error(err); 
  }

  updateEmailsList($event: any) {
    this.getEmails($event);
  }

  search() {
    this.resetFeedbackFlags();
    this.requestService.search(this.user._id, this.searchString, this.folderName)
    .subscribe({
      next: (res) => this.handleRespone(res),
      error: (e) => this.handleResponseError(e),
      complete: () => console.info('search completed!')
    })
  }

  sort(by: string) {
    this.resetFeedbackFlags();
    this.requestService.sort(by, this.folderName, this.user._id)
    .subscribe({
      next: (res) => this.handleRespone(res),
      error: (e) => this.handleResponseError(e),
      complete: () => console.info('sort completed!')
    })
  }

}
