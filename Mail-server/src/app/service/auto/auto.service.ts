import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Data } from 'src/app/model/Data';

@Injectable({
  providedIn: 'root'
})

export class AutoService {
  data!: Data
  isLoggedIn = false
  username = ""

  constructor(private router: Router,
    private http: HttpClient) { }

  auto(data: Data): boolean {
    if (this.isValidEmail(data.getEmail())) {
      this.data = data
      return true;
    }
    this.isLoggedIn = false;
    return false;
  }

  getData(): Data {
    return this.data;
  }

  isValidEmail(email: string): boolean {
    return email.includes('@mail.com') || email.includes('@gmail.com') || email.includes('@yahoo.com')
  }

  isValidPassword(password: string): boolean {
    return password.length >= 8 && password.length <= 20
  }

  signIn() {
    this.router.navigate(['register']);
  }
  
  signInCompleted() {
    console.log("navigate to inbox")
    this.router.navigate(['inbox']);
  }

  logout() {
    this.http.get("http://localhost:8081/logOut").subscribe()
    this.isLoggedIn = false;
    this.router.navigate(['']);
  }

  refresh() {
    this.http.get("http://localhost:8081/refresh").subscribe()
  }

  setUserName(body: any){
    this.username = body.firstName
    console.log(this.username)
  }

  getUserName(){
    return this.username
  }

}
