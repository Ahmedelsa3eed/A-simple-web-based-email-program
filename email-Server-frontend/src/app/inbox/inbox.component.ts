import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpEvent, HttpEventType } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Send } from "../model/Send";
import { AutoService } from "../service/auto/auto.service";
import { ModalDismissReasons, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { FormBuilder, NgForm } from "@angular/forms";
import { saveAs } from 'file-saver';


@Component({
  selector: 'cf-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css']
})
export class InboxComponent implements OnInit {

  filenames: string[] = [];
  fileStatus = { status: '', requestType: '', percent: 0 };

  constructor(private http: HttpClient, private modalService: NgbModal) { }
  friends: Send[]
  friendId: number
  closeResult: String = '';
  private deleteId: number | undefined;
  ngOnInit(): void {
    this.onn()
  }
  onn() {
    this.http.get<Send[]>("http://localhost:8081/inbox").subscribe(
      response => {
        this.friends = <Send[]>response
        for (let i = 0; i < this.friends.length; i++) {
          
          let date =this.friends[i].date.split("T");
          this.friends[i].date=date[0]+" "+date[1].split(".")[0]

          this.friends[i].id = i
        }
      }
    );
  }
  open(content: any) {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }
  openDetails(targetModal: any, friend: Send) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'lg'
    });
    this.friendId = friend.id
    // @ts-ignore
    document.getElementById('fromd').setAttribute('value', friend.from);
    // @ts-ignore
    document.getElementById('subjectd').setAttribute('value', friend.subject);
    // @ts-ignore
    document.getElementById('bodyd').setAttribute('value', friend.body); 
    // @ts-ignore
    document.getElementById('Attachmentsd').setAttribute('value', 'null');
    if(friend.attachmentsName.length!=0){
    // @ts-ignore
    document.getElementById('Attachmentsd').setAttribute('value', friend.attachmentsName[0]);
    
    }


  }

  openDelete(targetModal: any, friend: Send) {
    this.deleteId = friend.id;
    this.modalService.open(targetModal, {
      backdrop: 'static',
      size: 'lg'
    });
  }
  onDelete() {
    let oldEmail: Send = this.friends[this.deleteId];
    this.http.post('http://localhost:8081/deleteFromInbox', oldEmail)
      .subscribe();

    if (typeof this.deleteId === "number") {
      this.friends.splice(this.deleteId, 1)
    }
    for (let i = this.deleteId; i < this.friends.length; i++) {
      this.friends[i].id = i
    }
  }

  onLoad() {
    console.log("hh")
    return this.http.post<Send>("http://localhost:8081/inbox",
      {
        observe: "body"
      }).subscribe(
        (emails) => console.log(emails)
      )
  }
  onSearch(logInForm: NgForm) {
    if (!logInForm.valid) {
      return
    }
    console.log(logInForm.value.sorts)
    console.log(logInForm.value.search)
    this.http.get("http://localhost:8081/search",
      {
        params: {
          searchBar: logInForm.value.search,
          searchPosition: "inbox",
          searchBy: logInForm.value.sorts
        }
      }).subscribe(
        response => {
          this.friends = <Send[]>response
          for (let i = 0; i < this.friends.length; i++) {
            let date =this.friends[i].date.split("T");
            this.friends[i].date=date[0]+" "+date[1].split(".")[0]
            this.friends[i].id = i
          }
        }
      );
  }
  priority(logInForm: NgForm,friend :Send) {
    if (!logInForm.valid) {
      return
    }
    this.friends[friend.id].priority=logInForm.value.priority

    const emails=this.friends
    this.http.post('http://localhost:8081/priority', emails)
      .subscribe();

    console.log(logInForm.value.priority)
  }
  sort(logInForm: NgForm) {
    if (!logInForm.valid) {
      return
    }
    console.log("ho")
    this.http.get("http://localhost:8081/sort",
      {
        params: {
          sortPosition: "inbox",
          sortBy: logInForm.value.sort
        }
      }).subscribe();
    this.onn()
  }

  onDownload() {
    console.log("jelosdhng")
    const email: Send = this.friends[this.friendId]
    console.log(email.attachmentsName)
    const attachmentName = email.attachmentsName[0]
    this.http.get("http://localhost:8081/download/", {
      observe: 'events',
      params: {
        filename: email.attachmentPath,
        attachment: attachmentName
      },
      responseType: 'blob'
    }).subscribe(
      event => {
        console.log(event)
        this.resportProgress(event)
      }
    );
  }

  private resportProgress(httpEvent: HttpEvent<string[] | Blob>): void {
    switch (httpEvent.type) {
      case HttpEventType.ResponseHeader:
        console.log('Header returned', httpEvent);
        break;
      case HttpEventType.Response:
        if (httpEvent.body instanceof Array) {
          this.fileStatus.status = 'done';
          for (const filename of httpEvent.body) {
            this.filenames.unshift(filename);
          }
        } else {
          // saveAs(new File([httpEvent.body!], httpEvent.headers.get('File-Name')!,
          //   { type: `${httpEvent.headers.get('Content-Type')};charset=utf-8` }));
          saveAs(new Blob([httpEvent.body!],
            { type: `${httpEvent.headers.get('Content-Type')};charset=utf-8` }),
            httpEvent.headers.get('File-Name'));
        }
        this.fileStatus.status = 'done';
        break;
      default:
        console.log(httpEvent);
        break;

    }
  }


}


