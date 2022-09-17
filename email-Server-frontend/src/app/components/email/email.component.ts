import { Component, OnInit } from '@angular/core';
import {Email} from "../../models/Email";

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent implements OnInit {
  Email: Email = new Email();
  constructor() { }

  ngOnInit(): void {
  }

}
