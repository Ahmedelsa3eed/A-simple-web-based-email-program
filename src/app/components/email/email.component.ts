import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Email } from "../../models/Email";
import { User } from "../../models/User";
import { RequestService } from "../../services/request.service";
import { Router } from "@angular/router";
import { saveAs } from 'file-saver';
import {ignoreElements} from "rxjs";

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent implements OnInit {

  @Input() email = new Email();
  @Output() emailRemovedEvent = new EventEmitter();
  emailsList: any;
  @Input() user = new User();
  removeLoading: boolean = false;
  downloadLoading: boolean = false;

  constructor(private requestsService: RequestService,
    private router: Router) { }

  ngOnInit(): void {
  }

  removeEmail() {
    this.removeLoading = true;
    console.log("you are asking to delete"+this.email._id);
    console.log(this.router.url);
    if (this.router.url === 'home/emails/sent') {
      this.requestsService.deleteEmailFromDB(this.email, "Sent", this.user)
      .subscribe((res) => {
        this.removeLoading = false;
        console.log(res);
        this.emailRemovedEvent.emit(res.body);
      });
    }
    else if (this.router.url === 'home/emails/trash') {
      this.requestsService.deleteEmailFromDB(this.email,"Trash",this.user)
      .subscribe((res) => {
        this.removeLoading = false;
        console.log(res);
        this.emailRemovedEvent.emit(res.body);
      });
    }
    else if(this.router.url === 'home/emails/draft') {
      this.requestsService.deleteEmailFromDB(this.email,"Draft",this.user)
      .subscribe((res) => {
        this.removeLoading = false;
        console.log(res);
        this.emailRemovedEvent.emit(res.body);
      });
    }
    else{
      this.requestsService.deleteEmailFromInbox(this.email)
      .subscribe((res) => {
        this.removeLoading = false;
        if (res.ok) {
          console.log(res);
          this.emailRemovedEvent.emit(res.body);
        }
      })
    }
  }

  public markAsSeen() {
    if (!this.email.seen) {
      this.email.seen = true;
      this.requestsService.markAsSeen(this.email._id, this.user._id).subscribe(res => {
        console.log(res);
      })
    }
  }

  download(file: string) {
    let attachmentPosition ="";
    console.log(this.router.url)
    this.downloadLoading = true;
    if (this.router.url == '/home/emails/inbox' || this.router.url == '/home/emails/draft') {
      attachmentPosition = this.email.sender
    }else {
      attachmentPosition = this.email.receiver;
    }
    this.requestsService.downloadFile(file, attachmentPosition)
    .subscribe({
      next: (res) => {
        if(res.body) {
          saveAs(res.body, file);
        }
        this.downloadLoading = false;
      },
      error: (e) => {
        console.error(e);
      },
      complete: () => console.info('Downloading complete!')
    })
  }


}
