import {Component, Input, OnInit} from '@angular/core';
import {Email} from "../../models/Email";
import {UserService} from "../../services/user.service";
import {User} from "../../models/User";
import {RequestService} from "../../services/request.service";
import {NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent implements OnInit {

  @Input() email = new Email();
  user = new User();

  constructor(private userService: UserService,
    private requestsService: RequestService,
    private router: Router) { }

  ngOnInit(): void {
    this.setUser();
    this.handleRouterEvent();
  }

  removeEmail() {
    console.log("you are asking to delete"+this.email._id);
    console.log(this.router.url);
    if (this.router.url === '/home/sent') {
      this.requestsService.deleteEmailFromDB(this.email, "Sent", this.user)
      .subscribe((res) => {
          console.log(res);
        this.router.navigateByUrl('/home', { skipLocationChange: true })
          .then(() => {
            this.router.navigate(['/home/sent']);
          });      });
    }
    else if (this.router.url === '/home/trash') {
      this.requestsService.deleteEmailFromDB(this.email,"Trash",this.user)
      .subscribe((res) => {
        console.log(res);
        this.router.navigateByUrl('/home', { skipLocationChange: true })
          .then(() => {
            this.router.navigate(['/home/trash']);
          });      });
    }
    else if(this.router.url === '/home/draft') {
      this.requestsService.deleteEmailFromDB(this.email,"Draft",this.user)
      .subscribe((res) => {
          console.log(res);
        this.router.navigateByUrl('/home', { skipLocationChange: true })
          .then(() => {
            this.router.navigate(['/home/draft']);
          });      });
    }
    else{
      this.requestsService.deleteEmailFromInbox(this.email)
      .subscribe((res) => {
        if (res.ok) {
          console.log(res);
          this.router.navigateByUrl('/home', { skipLocationChange: true })
            .then(() => {
              this.router.navigate(['/home/inbox']);
            });

        }
        })
    }
  }

  public markAsSeen() {
    if (!this.email.seen) {
      this.email.seen = true;
      this.requestsService.markAsSeen(this.email._id, this.user._id).subscribe(res => {
        console.log(res);
      })
    }

  }

  private setUser() {
    this.userService.getUser().subscribe(res => {
      this.user = res;
      console.log(res);
    });
  }

  private handleRouterEvent() {
    this.router.events.subscribe((e) => {
      if (e instanceof NavigationEnd) {
        console.log(e.url);
      }
    });
  }

}
