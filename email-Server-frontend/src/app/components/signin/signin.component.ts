import { SigninService } from './../../services/signin.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User';
import { HttpStatusCode } from '@angular/common/http';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  public user: User;
  public isLoading: boolean = false;
  public isRefuesdLogin: boolean = false;

  constructor(private signinService: SigninService,
    private router: Router) {
    this.user = new User();
  }

  ngOnInit(): void {
  }

  submitForm() {
    this.isLoading = true;
    this.signinService.signIn(this.user)
    .subscribe({
      next: (res) => {
        console.log(res);
        if(res.status === HttpStatusCode.Ok) {
          this.router.navigateByUrl('/home');
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
