import { Component, OnInit } from '@angular/core';
import {Send} from "../model/Send";
import {AutoService} from "../service/auto/auto.service";
import {HttpClient} from "@angular/common/http";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {FormBuilder,FormGroup} from "@angular/forms";

@Component({
  selector: 'cf-sent',
  templateUrl: './sent.component.html',
  styleUrls: ['./sent.component.css']
})
export class SentComponent implements OnInit {
  constructor(private http: HttpClient,private modalService: NgbModal, private fb: FormBuilder) { }
  friends:Send[]
  closeResult: String='';
  private deleteId: number | undefined;
  ngOnInit(): void {
    this.onn()
  }
  onn() {
    this.http.get<Send[]>("http://localhost:8081/sent").subscribe(
      response => {
        this.friends = <Send[]>response
        for (let i=0;i<this.friends.length;i++){
          this.friends[i].id=i
        }
      }
    );
  }
  open(content: any) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
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
    // @ts-ignore
    document.getElementById('tod').setAttribute('value', friend.to);
    // @ts-ignore
    document.getElementById('subjectd').setAttribute('value', friend.subject);
    // @ts-ignore
    document.getElementById('bodyd').setAttribute('value', friend.body);
  }

  openDelete(targetModal: any, friend: Send) {
    this.deleteId = friend.id;
    this.modalService.open(targetModal, {
      backdrop: 'static',
      size: 'lg'
    });
  }
  onDelete() {
    if (typeof this.deleteId === "number") {
      this.friends.splice(this.deleteId, 1)
    }
    for (let i=this.deleteId;i<this.friends.length;i++){
      this.friends[i].id=i
    }
    const newEmails = this.friends
    this.http.post('http://localhost:8081/deleteFromSent', newEmails)
      .subscribe()
  }

}


