import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  @Output() folderNameEvent = new EventEmitter<string>();

  constructor() { }

  ngOnInit(): void {
  }

  sendFolderName(folderName: string) {
    this.folderNameEvent.emit(folderName);
  }
}
