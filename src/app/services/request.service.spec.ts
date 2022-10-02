import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { RequestService } from 'src/app/services/request.service';

describe('RequestService', () => {
    let service: RequestService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [ HttpClientModule ],
            providers: [ RequestService ]
        });
        service = TestBed.inject(RequestService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
