import { TestBed } from '@angular/core/testing';

import { ModalService } from './modal.service';

describe('ModalService', () => {
  let service: ModalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ModalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('the receiver should be null', () => {
    (done: DoneFn) => {
      service.getReceiver().subscribe(res => {
        expect(res).toBeNull();
        done();
      })
    }
  });

  it('the receiver shouldn\'t be null', () => {
    service.setReceiver('ahmed@mail.com');
    (done: DoneFn) => {
      service.getReceiver().subscribe(res => {
        expect(res).toBe('ahmed@mail.com');
        done();
      })
    }
  });
});
