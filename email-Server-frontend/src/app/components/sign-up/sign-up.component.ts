import { SignUpService } from './../../services/sign-up.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  public user: User;
  public isLoading: boolean = false;
  public isRefuesdLogin: boolean = false;

  constructor(private router: Router, private signUpService: SignUpService) {
    this.user = new User();
  }

  ngOnInit(): void {
  }

  submitForm() {
    this.isLoading = true;
    this.signUpService.signUp(this.user)
    .subscribe({
      next: (res) => {
        console.log(res);
        if(res.ok) {
          this.router.navigateByUrl('/');
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
