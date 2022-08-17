import { Component, OnInit } from '@angular/core';
import { NgForm } from "@angular/forms";
import { AutoService } from "../service/auto/auto.service";
import { User } from "../model/User";
import { HttpClient, HttpStatusCode } from "@angular/common/http";

@Component({
  selector: 'cf-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private autoService: AutoService,
    private http: HttpClient) { }

  notValid = false
  errMsg = ""

  ngOnInit(): void {}

  onSubmit(signInForm: NgForm) {
    if (!signInForm.valid) {
      this.errMsg = "Please fill all fields!"
      this.notValid = true;
      return;
    }

    const user = new User(
      signInForm.value.first,
      signInForm.value.last,
      signInForm.value.email,
      signInForm.value.password)

    if (!this.autoService.isValidEmail(user.getEmail())) {
      this.errMsg = "Not valid Email!"
      this.autoService.isLoggedIn = false
      this.notValid = true
      return;
    }

    if (!this.autoService.isValidPassword(user.getPassword())) {
      this.errMsg = "Password should contains at least 8 characters!"
      this.autoService.isLoggedIn = false
      this.notValid = true
      return;
    }

    this.http.post<User>('http://localhost:8081/register', user, {
      observe: 'response'
    })
    .subscribe(
      (response) => {
        console.log(response);
        if (response.status == HttpStatusCode.Created) {//CREATED
          this.autoService.signInCompleted()
        } 
        else {//not acceptable
          this.errMsg = "Not Available Email!"
          this.notValid = true
        }
      })
  }
}
