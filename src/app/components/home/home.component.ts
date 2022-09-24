import { AttachmentService } from './../../services/attachment.service';
import { LocalStorageWrapper } from './../../services/localStorageWrapper.service';
import { RequestService } from './../../services/request.service';
import { Email } from './../../models/Email';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { HttpResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  public user: User;
  public email: Email;
  public folderName: string = "";
  public isLoading: boolean = false;
  public isRefuesdSend: boolean = false;
  public uploadLoading: boolean = false;
  public sendSuccess: boolean = false;
  public uploadSuccess: boolean = false;
  public uploadError: boolean = false;
  public addingToDraft: boolean = false;

  constructor(private requestService: RequestService,
    private userService: LocalStorageWrapper,
    private attachmentService: AttachmentService) {
      this.user = new User;
      this.email = new Email;
  }

  ngOnInit(): void {
    this.user = this.userService.getUser();
  }

  sendEmail() {
    this.prepareData();
    this.isLoading = true;
    this.requestService.sendEmail(this.email, this.user)
    .subscribe({
      next: (res) => this.emailResponseHandler(res),
      error: (e) => this.emailResponseErrorHandler(e),
      complete: () => console.info('sent successfully!')
    })
  }
  
  private prepareData() {
    this.email.sender = this.user.email;
    this.email.date = new Date();
  }
  
  private emailResponseHandler(res: HttpResponse<boolean>) {
    this.isLoading = false;
    if (res.ok) {
      this.isRefuesdSend = false;
      this.sendSuccess = true;
    }
    else {
      this.isRefuesdSend = true;
      this.sendSuccess = false;
    }
  }

  private emailResponseErrorHandler(err: any) {
    this.isLoading = false;
    this.isRefuesdSend = true;
    this.sendSuccess = false;
    console.error(err);
  }

  addToDraft() {
    this.prepareData();
    this.addingToDraft = true;
    this.requestService.addToDraft(this.email, this.user)
    .subscribe({
      next: (res) => {
        this.addingToDraft = false;
        this.emailResponseHandler(res);
      },
      error: (e) => {
        this.addingToDraft = false;
        this.emailResponseErrorHandler(e);
      },
      complete: () => console.info('added to draft successfully!')
    })
  }

  onClose(form: NgForm) {
    form.resetForm();
  }

  onFileSelect(event: any) {
    const attachments = this.prepareAttachments(event);
    this.uploadLoading = true;
    this.attachmentService.uploadFiles(attachments, this.user._id)
    .subscribe({
      next: (res) => this.uploadResponseHandler(res),
      error: (e) => {
        this.uploadLoading = false;
        this.wrongUploadResponseHandler();
        console.error(e);
      },
      complete: () => console.info('Upload completed!')
    })
  }

  private prepareAttachments(event: any) {
    const attachments = new FormData();
    const files: File[] = event.target.files;
    this.email.attachments = [];
    for (const file of files) {
      attachments.append('files', file, file.name);
      this.email.attachments.push(file.name);
    }
    return attachments;
  }

  private uploadResponseHandler(res: HttpResponse<boolean>) {
    this.uploadLoading = false;
    if (res.ok) {
      this.uploadSuccess = true;
      this.uploadError = false;
    }
    else {
      this.wrongUploadResponseHandler();
    } 
  }

  private wrongUploadResponseHandler() {
    this.email.attachments = [];
    this.uploadError = true;
    this.uploadSuccess = false;
  }

}