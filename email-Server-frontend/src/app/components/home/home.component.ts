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
  public folderName: string = "";

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
    this.requestService.sendEmail(this.email)
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

  prepareData() {
    this.isLoading = true;
    this.email.sender = this.user.email;
    this.email.date = new Date();
  }

  onFileSelect(event: any) {
    for(const file of event.target.files) {
      this.email.attachments.append('files', file, file.name);
    }
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
