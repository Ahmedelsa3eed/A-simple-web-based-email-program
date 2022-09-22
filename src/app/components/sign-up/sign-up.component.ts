import { RequestService } from './../../services/request.service';
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

  constructor(private router: Router, private requestService: RequestService) {
    this.user = new User();
  }

  ngOnInit(): void {
  }

  submitForm() {
    this.isLoading = true;
    this.requestService.validateUser(this.user, 'register')
    .subscribe({
      next: (res) => {
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
