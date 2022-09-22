import { LocalStorageWrapper } from './../../services/localStorageWrapper.service';
import { Router } from '@angular/router';
import { RequestService } from './../../services/request.service';
import { Email } from './../../models/Email';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  public isLoading: boolean = false;
  public isRefuesdSend: boolean = false
  public sendSuccess: boolean = false;
  public email: Email;
  public user: User;
  public folderName: string = "";

  constructor(private requestService: RequestService,
    private router: Router,
    private userService: LocalStorageWrapper) {
    this.email = new Email;
    this.user = new User;
  }

  ngOnInit(): void {
    this.setUser();
  }

  private setUser() {
    this.user = this.userService.getUser();
  }

  sendEmail() {
    this.prepareData();
    this.requestService.sendEmail(this.email, this.user)
    .subscribe({
      next: (res) => {
        if (res.ok) {
          this.isRefuesdSend = false;
          this.sendSuccess = true;
          this.handleResponse(res);
        }else {
          this.isRefuesdSend = true;
          this.sendSuccess = false;
        }
        this.isLoading = false;
      },
      error: (e) => {
        this.isLoading = false;
        this.isRefuesdSend = true;
        this.sendSuccess = false;

        console.error(e);
      },
      complete: () => console.info('complete')
    })
  }

  private prepareData() {
    this.isLoading = true;
    this.email.sender = this.user.email;
    this.email.date = new Date();
  }

  private handleResponse(res: HttpResponse<boolean>) {
    console.log(res);
    this.isLoading = false;
    if (res.ok) {
      // TODO: what should we do after sending the email
      this.router.navigateByUrl('/home/emails/inbox');
    }
    else {
      window.alert(`returned status code: ${res.status}`);
      this.isRefuesdSend = true;
    }
  }

  onFileSelect(event: any) {
    // prepare formData
    const attachments = new FormData();
    const files: File[] = event.target.files;
    this.email.attachments = [];
    for(const file of files) {
      attachments.append('files', file, file.name);
      this.email.attachments.push(file.name);
    }
    // uploda formData
    this.requestService.uploadFiles(attachments, this.user._id)
    .subscribe({
      next: (res) => {
        console.log(res);
      },
      error: (e) => {
        console.error(e);
      },
      complete: () => console.info('Upload completeed!')
    })
  }

}
