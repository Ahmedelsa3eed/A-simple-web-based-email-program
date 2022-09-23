import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {

  headerSource = new BehaviorSubject<boolean>(false);

  constructor() { }

  getSignedInResults(): Observable<boolean> {
    return this.headerSource.asObservable();
  }

  setSignedInResults(value: boolean) {
    this.headerSource.next(value);
  }

}
