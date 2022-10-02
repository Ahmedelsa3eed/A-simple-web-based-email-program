import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AttachmentService } from 'src/app/services/attachment.service';
import { ModalService } from 'src/app/services/modal.service';
import { RequestService } from 'src/app/services/request.service';

import { EmailComponent } from './email.component';

describe('EmailComponent', () => {
  let component: EmailComponent;
  let fixture: ComponentFixture<EmailComponent>;

  const requestServiceSpy = jasmine.createSpyObj('RequestService', ['sendEmail', 'addToDraft']);
  const routerSpy = jasmine.createSpyObj('Router', [], ['url']);
  const attachmentServiceSpy = jasmine.createSpyObj('AttachmentService', ['uploadFiles']);
  const modalServiceSpy = jasmine.createSpyObj('ModalServiceSpy', ['getReceiver', 'getSubject', 'getBody']);


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmailComponent ],
      imports: [ FormsModule ],
      providers: [
        { provide: RequestService, useValue: requestServiceSpy },
        { provide: Router, useValue: routerSpy},
        { provide: AttachmentService, useValue: attachmentServiceSpy},
        { provide: ModalService, useValue: modalServiceSpy},
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
