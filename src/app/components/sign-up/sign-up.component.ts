import { RequestService } from './../../services/request.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  public user: User;
  public isLoading: boolean = false;
  public isRefuesdLogin: boolean = false;

  constructor(private router: Router, private requestService: RequestService) {
    this.user = new User();
  }

  ngOnInit(): void {
  }

  submitForm() {
    this.prepareProgressFlags();
    this.requestService.validateUser(this.user, 'register')
    .subscribe({
      next: (res) => this.handleSignInResponse(res),
      error: (err) => this.handleSingInError(err),
      complete: () => console.info('SignUp completed successfully!')
    })
  }

  private prepareProgressFlags(): void {
    this.isLoading = true;
    this.isRefuesdLogin = false;
  }

  private handleSignInResponse(res: HttpResponse<User>): void {
    if(res.ok) {
      this.router.navigateByUrl('/');
    }
    else {
      window.alert(`returned status code: ${res.status}`);
      this.isRefuesdLogin = true;
    }
    this.isLoading = false;
  }

  private handleSingInError(err: any): void {
    this.isLoading = false;
    this.isRefuesdLogin = true;
    console.error(err);
  }

}
