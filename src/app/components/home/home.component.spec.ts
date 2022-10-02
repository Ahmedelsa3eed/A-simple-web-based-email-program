import { FormsModule } from '@angular/forms';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';

import { LocalStorageWrapper } from './../../services/localStorageWrapper.service';
import { RequestService } from 'src/app/services/request.service';
import { AttachmentService } from 'src/app/services/attachment.service';
import { ModalService } from 'src/app/services/modal.service';
import { of } from 'rxjs';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  const requestServiceSpy = jasmine.createSpyObj('RequestService', ['sendEmail', 'addToDraft']);
  const userServiceSpy = jasmine.createSpyObj('LocalStorageWrapper', ['getUser']);
  const attachmentServiceSpy = jasmine.createSpyObj('AttachmentService', ['uploadFiles']);
  const modalServiceSpy = jasmine.createSpyObj('ModalServiceSpy', ['getReceiver', 'getSubject', 'getBody']);

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomeComponent ],
      imports: [ FormsModule ],
      providers: [
        { provide: RequestService, useValue: requestServiceSpy },
        { provide: LocalStorageWrapper, useValue: userServiceSpy},
        { provide: AttachmentService, useValue: attachmentServiceSpy},
        { provide: ModalService, useValue: modalServiceSpy},
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    modalServiceSpy.getReceiver.and.returnValue(of(''));
    modalServiceSpy.getSubject.and.returnValue(of(''));
    modalServiceSpy.getBody.and.returnValue(of(''));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
