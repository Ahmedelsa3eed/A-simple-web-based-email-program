import { FormsModule } from '@angular/forms';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SigninComponent } from './signin.component';

import { RequestService } from './../../services/request.service';
import { Router } from '@angular/router';
import { LocalStorageWrapper } from 'src/app/services/localStorageWrapper.service';
import { HeaderService } from 'src/app/services/header.service';

describe('SigninComponent', () => {
  let component: SigninComponent;
  let fixture: ComponentFixture<SigninComponent>;
  
  const routerSpy = jasmine.createSpyObj('Router', ['navigateByUrl']);
  const requestServiceSpy = jasmine.createSpyObj('RequestService', ['validateUser']);
  const userServiceSpy = jasmine.createSpyObj('LocalStorageWrapper', ['clear', 'saveUser']);
  const headerServiceSpy = jasmine.createSpyObj('HeaderService', ['setSignedInResults']);

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SigninComponent ],
      imports: [ FormsModule ],
      providers: [
        { provide: RequestService, useValue: requestServiceSpy },
        { provide: Router, useValue: routerSpy },
        { provide: LocalStorageWrapper, useValue: userServiceSpy },
        { provide: HeaderService, useValue: headerServiceSpy },
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SigninComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
