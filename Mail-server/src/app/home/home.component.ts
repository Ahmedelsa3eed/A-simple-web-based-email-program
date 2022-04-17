import { Component, OnInit } from '@angular/core';
import { NgForm } from "@angular/forms";
import { AutoService } from "../service/auto/auto.service";
import { Send } from "../model/Send";
import { HttpClient } from "@angular/common/http";

@Component({
  selector: 'cf-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  constructor(private autoService: AutoService,
    private http: HttpClient) { }

  isFormNCom = false;
  inCorrectMail = false;
  isDraft = false

  ngOnInit() { }

  onFileSelect(event: any): void {

    const formData = new FormData()
    const files: File[] = event.target.files

    for (const file of files)
      formData.append('files', file, file.name)

    this.http.post<any>('http://localhost:8081/upload', formData).subscribe(
      (res) => console.log(res)
    );
  }

  onSubmit(sendForm: NgForm) {
    if (!sendForm.valid) {
      this.isFormNCom = true;
      this.inCorrectMail = false;
      return
    }
    const email = new Send(
      this.autoService.getData().getEmail(),
      sendForm.value.to,
      sendForm.value.subject,
      sendForm.value.body)
    console.log(email)

    if (!this.autoService.isValidEmail(email.getTo())) {
      this.isFormNCom = false;
      this.inCorrectMail = true;
      return
    }

    this.isFormNCom = false;
    this.inCorrectMail = false;
    
    if(this.isDraft){
      this.http.post<any>('http://localhost:8081/addToDraft', email).subscribe(
        (res) => console.log(res)
      );
    }else{
      this.http.post<any>('http://localhost:8081/send', email).subscribe(
      (res) => console.log(res)
      );
    }
  }

  toDraft() {
    this.isDraft = true
  }
}