import { User } from '../model/User';
import { catchError, throwError } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { NgForm } from "@angular/forms";
import { AutoService } from "../service/auto/auto.service";
import { Data } from "../model/Data";
import { HttpClient, HttpErrorResponse, HttpStatusCode} from "@angular/common/http";
import { Router } from "@angular/router";

@Component({
  selector: 'cf-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(private autoService: AutoService,
              private http: HttpClient,
              private router: Router) { }
  
  FormNotComplete = false
  NotValid = false
  err: any
  username: any

  ngOnInit(): void {}

  onSubmit(logInForm: NgForm) {

    if (!logInForm.valid) {
      this.FormNotComplete = true;
      return;
    }
    const user = new Data(logInForm.value.inputEmail, logInForm.value.password);
    if (!this.autoService.isValidEmail(user.getEmail())) {
      this.err = "Not valid email!"
      this.NotValid = true;
      return;
    }

    this.http.post<User>('http://localhost:8081/signIn', user, {
      observe: 'response'
    })
    .pipe(
      catchError(this.handleError)
    )
    .subscribe((response) => {
      if(response.status == HttpStatusCode.Ok){
        this.autoService.setUserName(response.body)
        this.router.navigate(['inbox']);
        this.autoService.isLoggedIn = true;
      }else{
        this.setAlartMsg("Try Again!")
      }
    })
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('CONNECTION REFUSED:', error.error);
    } 
    else{
      console.error(`Backend error with code ${error.status}, with body:`, error.error);
    }
    return throwError(() => new Error(error.error));
  }

  setAlartMsg(str: string){
    this.err = str
    this.NotValid = true
  }

  signin() {
    this.autoService.signIn()
  }
}
