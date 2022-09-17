import { UserService } from './../../services/user.service';
import { Router } from '@angular/router';
import { RequestService } from './../../services/request.service';
import { Email } from './../../models/Email';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';

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
    this.userService.getUser().subscribe(res => {
      this.user = res;
      console.log(res);
    })
  }

  sendEmail() {
    this.isLoading = true;
    this.email.sender = this.user.email;
    this.requestService.sendEmail(this.email)
    .subscribe({
      next: (res) => {
        console.log(res);
        if(res.ok) {
          this.router.navigateByUrl('./inbox');
        }
        else {
          window.alert(`returned status code: ${res.status}`);
          this.isRefuesdSend = true;
        }
        this.isLoading = false;
      },
      error: (e) => {
        this.isLoading = false;
        this.isRefuesdSend = true;
        console.error(e);
      },
      complete: () => console.info('complete')
    })
  }

}
