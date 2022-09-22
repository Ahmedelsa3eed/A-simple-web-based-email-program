import { UserService } from './../../services/user.service';
import { RequestService } from '../../services/request.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  public user: User;
  public isLoading: boolean = false;
  public isRefuesdLogin: boolean = false;

  constructor(private requestService: RequestService,
    private router: Router,
    private userService: UserService) {
    this.user = new User();
  }

  ngOnInit(): void {
  }

  submitForm() {
    this.isLoading = true;
    this.requestService.validateUser(this.user, 'signIn')
    .subscribe({
      next: (res) => {
        console.log(res);
        if(res.ok && res.body != null) {
          this.userService.setUser(res.body);
          this.router.navigateByUrl('/home/inbox');
        }
        else {
          window.alert(`returned status code: ${res.status}`);
          this.isRefuesdLogin = true;
        }
        this.isLoading = false;
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
