import { ModalService } from './../../services/modal.service';
import { HttpResponse } from '@angular/common/http';
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
  exampleModal = document.getElementById('exampleModal');

  constructor(private requestsService: RequestService,
    public router: Router,
    private attachmentService: AttachmentService,
    private modalService: ModalService) { }

  ngOnInit(): void {
  }

  removeEmail() {
    this.removeLoading = true;
    if (this.router.url === '/home/emails/sent') {
      this.requestsService.deleteEmail(this.email, 'sent', this.user)
      .subscribe((res) => this.handleRemoveResponse(res, 'sent'));
    }
    else if (this.router.url === '/home/emails/trash') {
      this.requestsService.deleteEmail(this.email, 'trash', this.user)
      .subscribe((res) => this.handleRemoveResponse(res, 'trash'));
    }
    else if (this.router.url === '/home/emails/draft') {
      this.requestsService.deleteEmail(this.email, 'draft', this.user)
      .subscribe((res) => this.handleRemoveResponse(res, 'draft'));
    }
    else if (this.router.url === '/home/emails/inbox'){
      this.requestsService.deleteEmail(this.email, 'inbox', this.user)
      .subscribe((res) => this.handleRemoveResponse(res, 'inbox'))
    }
  }

  private handleRemoveResponse(res: HttpResponse<boolean>, folderName: string) {
    this.removeLoading = false;
    if (res.body) {
      this.emailRemovedEvent.emit(folderName);
      console.info('email is removed successfully!');
    }
    else {
      console.info('email can\'t be removed!');
    }
  }

  public markAsSeen() {
    if (!this.email.seen && this.router.url === '/home/emails/inbox') {
      this.email.seen = true;
      this.requestsService.markAsSeen(this.email._id, this.user._id)
      .subscribe(res => console.log(`Marked: ${res.body}`))
    }
  }

  download(file: string) {
    this.downloadLoading = true;
    let attachmentPosition = this.email.sender;
    this.attachmentService.downloadFile(file, attachmentPosition)
    .subscribe({
      next: (res) => {
        if(res.body) {
          saveAs(res.body, file);
        }
        this.downloadLoading = false;
      },
      error: (e) => console.error(e),
      complete: () => console.info('Downloading complete!')
    })
  }

  undoRemoveFromInbox() {
    this.removeLoading = true;
    this.requestsService.undoRemoveFromInbox(this.email._id, this.user._id)
    .subscribe((res) => {
      this.removeLoading = false;
      if(res.body) {
        console.info('undo remove from inbox is done!');
        this.emailRemovedEvent.emit('trash');
      }
    })
  }

  updateModalContent() {
    this.modalService.setReceiver(this.email.sender);
    this.modalService.setSubject(this.email.subject);
    this.modalService.setBody(this.email.body);
  }

  updateReceiverModalContent() {
    this.modalService.setReceiver(this.email.sender);
  }
}
