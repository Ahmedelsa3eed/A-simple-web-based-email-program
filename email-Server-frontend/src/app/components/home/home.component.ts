import { Router } from '@angular/router';
import { RequestService } from './../../services/request.service';
import { Email } from './../../models/Email';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  email: Email;

  constructor(private requestService: RequestService,
    private router: Router) {
    this.email = new Email;
  }

  ngOnInit(): void {
  }

  sendEmail() {
    this.requestService.sendEmail(this.email)
    .subscribe({
      next: (res) => {
        console.log(res);
        if(res.ok) {
          this.router.navigateByUrl('/inbox');
        }
        else {
          window.alert(`returned status code: ${res.status}`);
          // this.isRefuesdLogin = true;
        }
        // this.isLoading = false;
      },
      error: (e) => {
        // this.isLoading = false;
        // this.isRefuesdLogin = true;
        console.error(e);
      },
      complete: () => console.info('complete')
    })
  }

}
