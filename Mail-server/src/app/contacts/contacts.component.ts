import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {Data} from "../model/Data";
import {Router} from "@angular/router";
import {Contact} from "../model/Contact";
import {Send} from "../model/Send";
import {AutoService} from "../service/auto/auto.service";
import {HttpClient} from "@angular/common/http";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {FormBuilder,FormGroup} from "@angular/forms";


@Component({
  selector: 'cf-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {


  constructor(private autoService: AutoService, private http: HttpClient, private router: Router,private modalService: NgbModal, private fb: FormBuilder) { }
  contacts:Contact[]
  editForm: any;
  closeResult: String='';
  friendId:number;
  isFormNCom=false
  private deleteId: number | undefined;
  ngOnInit(): void {
    this.onn()
    this.editForm = this.fb.group({
      id: [],
      namee: [''],
      email1e: [''],
      email2e: [''],
      email3e: [''],
      email4e: [''],
      email5e: [''],
      email6e: [''],
    } );
  }
  onn() {
    this.http.get<Contact[]>("http://localhost:8081/contacts").subscribe(
      response => {
        this.contacts = <Contact[]>response
        for (let i = 0; i < this.contacts.length; i++) {
          this.contacts[i].id = i
        }
      }
    );
    
  }
  onsave(){
    console.log(this.editForm.value.id)
    
    this.contacts[this.editForm.value.id].name=this.editForm.value.namee;
    this.contacts[this.editForm.value.id].emails[0]=this.editForm.value.email1e;
    this.contacts[this.editForm.value.id].emails[1]=this.editForm.value.email2e;
    this.contacts[this.editForm.value.id].emails[2]=this.editForm.value.email3e;
    this.contacts[this.editForm.value.id].emails[3]=this.editForm.value.email4e;
    this.contacts[this.editForm.value.id].emails[4]=this.editForm.value.email5e;
    this.contacts[this.editForm.value.id].emails[5]=this.editForm.value.email6e;

    const newContacts = this.contacts
    this.http.post('http://localhost:8081/editContact', newContacts)
      .subscribe();
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
  openDetails(targetModal: any, friend: Contact) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'lg'
    });
    this.friendId=friend.id
    // @ts-ignore
    document.getElementById('named').setAttribute('value', friend.name);
    // @ts-ignore
    document.getElementById('email1d').setAttribute('value', friend.emails[0]);
    // @ts-ignore
    document.getElementById("email2d").setAttribute('value', friend.emails[1]);
    // @ts-ignore
    document.getElementById("email3d").setAttribute('value', friend.emails[2]);
    // @ts-ignore
    document.getElementById("email4d").setAttribute('value', friend.emails[3]);
    // @ts-ignore
    document.getElementById("email5d").setAttribute('value', friend.emails[4]);
    // @ts-ignore
    document.getElementById("email6d").setAttribute('value', friend.emails[5]);
  }
  openEdit(targetModal: any, friend: Contact) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'lg'
    });
    this.editForm.patchValue( {
      id: friend.id,
      namee: friend.name,
      email1e: friend.emails[0],
      email2e: friend.emails[1],
      email3e: friend.emails[2],
      email4e: friend.emails[3],
      email5e: friend.emails[4],
      email6e: friend.emails[5],
    });
  }
  openDelete(targetModal: any, friend: Contact) {
    this.deleteId = friend.id;
    this.modalService.open(targetModal, {
      backdrop: 'static',
      size: 'lg'
    });
  }
  onDelete() {
    if (typeof this.deleteId === "number") {
      this.contacts.splice(this.deleteId, 1)
    }
    for (let i=this.deleteId;i<this.contacts.length;i++){
      this.contacts[i].id=i
    }
    const newContacts = this.contacts
    this.http.post('http://localhost:8081/deleteContact', newContacts)
      .subscribe()
  }

  is2empty(id:number):number{
    return this.contacts[id].emails.length
  }

  onSubmit(f: NgForm) {
    console.log(f.valid)
    if (!f.valid) {
      this.isFormNCom = true;
      return
    }
    this.isFormNCom=false
    let x:string=f.value.name
    console.log(x)
    this.contacts.push(new Contact(x, []))
    const newContacts = this.contacts
    this.http.post('http://localhost:8081/addContact', newContacts).subscribe();
    this.ngOnInit(); //reload the table
    this.modalService.dismissAll(); //dismiss the modal
  }

}

