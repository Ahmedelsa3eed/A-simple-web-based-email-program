import { AttachmentService } from './../../services/attachment.service';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Email } from "../../models/Email";
import { User } from "../../models/User";
import { RequestService } from "../../services/request.service";
import { Router } from "@angular/router";
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent implements OnInit {

  @Input() email = new Email();
  @Output() emailRemovedEvent = new EventEmitter<String>();
  emailsList: any;
  @Input() user = new User();
  removeLoading: boolean = false;
  downloadLoading: boolean = false;

  constructor(private requestsService: RequestService,
    private router: Router,
    private attachmentService: AttachmentService) { }

  ngOnInit(): void {
  }

  removeEmail() {
    this.removeLoading = true;
    console.log("you are asking to delete" + this.email._id);
    console.log(this.router.url);
    if (this.router.url === '/home/emails/sent') {
      this.requestsService.deleteEmail(this.email, 'sent', this.user)
      .subscribe((res) => {
        this.removeLoading = false;
        console.log(res);
        this.emailRemovedEvent.emit('sent');
      });
    }
    else if (this.router.url === '/home/emails/trash') {
      this.requestsService.deleteEmail(this.email, 'trash', this.user)
      .subscribe((res) => {
        this.removeLoading = false;
        console.log(res);
        this.emailRemovedEvent.emit('trash');
      });
    }
    else if (this.router.url === '/home/emails/draft') {
      this.requestsService.deleteEmail(this.email, 'draft', this.user)
      .subscribe((res) => {
        this.removeLoading = false;
        console.log(res);
        this.emailRemovedEvent.emit('draft');
      });
    }
    else if (this.router.url === '/home/emails/inbox'){
      console.log("delete from inbox");
      this.requestsService.deleteEmail(this.email, 'inbox', this.user)
      .subscribe((res) => {
        this.removeLoading = false;
        if (res.ok) {
          console.log(res);
          this.emailRemovedEvent.emit('inbox');
        }
      })
    }
  }

  public markAsSeen() {
    if (!this.email.seen && this.router.url === '/home/emails/inbox') {
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
    attachmentPosition = this.email.sender
    this.attachmentService.downloadFile(file, attachmentPosition)
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
