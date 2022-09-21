import { UserService } from './../../services/user.service';
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
  public isRefuesdSend: boolean = false;
  public email: Email;
  public user: User;

  constructor(private requestService: RequestService,
    private router: Router,
    private userService: UserService) {
    this.email = new Email;
    this.user = new User;
  }

  ngOnInit(): void {
    this.setUser();
  }

  sendEmail() {
    this.prepareData();
    this.requestService.sendEmail(this.email, this.user)
    .subscribe({
      next: (res) => {
        this.handleResponse(res);
      },
      error: (e) => {
        this.isLoading = false;
        this.isRefuesdSend = true;
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

  onFileSelect(event: any) {
    // prepare formData
    const attachments = new FormData();
    const files: File[] = event.target.files;

    for(const file of files) {
      attachments.append('files', file, file.name);
    }
    // uploda formData
    this.requestService.uploadFiles(attachments)
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

  private setUser() {
    this.userService.getUser().subscribe(res => {
      this.user = res;
      console.log(res);
    });
  }

  private handleResponse(res: HttpResponse<boolean>) {
    console.log(res);
    this.isLoading = false;
    if (res.ok) {
      // TODO: what should we do after sending the email
      this.router.navigateByUrl('/home/inbox');
    }
    else {
      window.alert(`returned status code: ${res.status}`);
      this.isRefuesdSend = true;
    }
  }

}
