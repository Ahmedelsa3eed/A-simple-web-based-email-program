import { LocalStorageWrapper } from './../../services/localStorageWrapper.service';
import { RequestService } from '../../services/request.service';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User';
import { HttpResponse } from '@angular/common/http';

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
    private userService: LocalStorageWrapper) {
    this.user = new User();
  }

  ngOnInit(): void {
  }

  submitForm() {
    this.prepareProgressFlags();
    this.requestService.validateUser(this.user, 'signIn')
    .subscribe({
      next: (res) => this.handleSignInResponse(res),
      error: (e) => this.handleSingInError(e),
      complete: () => console.info('Sign in completed successfully!')
    })
  }

  private prepareProgressFlags(): void {
    this.isLoading = true;
    this.isRefuesdLogin = false;
  }

  private handleSignInResponse(res: HttpResponse<User>): void {
    if (res.ok && res.body != null) {
      this.userService.saveUser(res.body);
      this.router.navigateByUrl('/home/emails/inbox');
    }
    else {
      window.alert(`returned status code: ${res.status}`);
      this.isRefuesdLogin = true;
    }
    this.isLoading = false;
  }

  private handleSingInError(e: any): void {
    this.isLoading = false;
    this.isRefuesdLogin = true;
    console.error(e);
  }

}
