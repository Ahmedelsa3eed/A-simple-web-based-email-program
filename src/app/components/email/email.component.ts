import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Email} from "../../models/Email";
import {UserService} from "../../services/user.service";
import {User} from "../../models/User";
import {RequestService} from "../../services/request.service";
import {NavigationEnd, Router} from "@angular/router";
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent implements OnInit {

  @Input() email = new Email();
  @Output() emailRemovedEvent = new EventEmitter();
  emailsList: any;
  @Input() user = new User();
  
  constructor(private userService: UserService,
    private requestsService: RequestService,
    private router: Router) { }
    
  ngOnInit(): void {
  }

  removeEmail() {
    console.log("you are asking to delete"+this.email._id);
    console.log(this.router.url);
    if (this.router.url === '/emails/sent') {
      this.requestsService.deleteEmailFromDB(this.email, "Sent", this.user)
      .subscribe((res) => {
        console.log(res);
        this.emailRemovedEvent.emit(res.body);
      });
    }
    else if (this.router.url === '/emails/trash') {
      this.requestsService.deleteEmailFromDB(this.email,"Trash",this.user)
      .subscribe((res) => {
        console.log(res);
        this.emailRemovedEvent.emit(res.body);
      });
    }
    else if(this.router.url === '/emails/draft') {
      this.requestsService.deleteEmailFromDB(this.email,"Draft",this.user)
      .subscribe((res) => {
        console.log(res);
        this.emailRemovedEvent.emit(res.body);
      });
    }
    else{
      this.requestsService.deleteEmailFromInbox(this.email)
      .subscribe((res) => {
        if (res.ok) {
          console.log(res);
          this.emailRemovedEvent.emit(res.body);
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

  download(file: string) {
    this.requestsService.downloadFile(file, this.user)
    .subscribe({
      next: (res) => {
        if(res.body) {
          saveAs(res.body, file);
        }
      },
      error: (e) => {
        console.error(e);
      },
      complete: () => console.info('Downloading complete!')
    })
  }


}
