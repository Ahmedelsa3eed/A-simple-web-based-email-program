import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  userSource = new BehaviorSubject<User>(new User);

  constructor() { }

  getUser(): Observable<User> {
    return this.userSource.asObservable();
  }

  setUser(customer: User) {
    this.userSource.next(customer);
  }

}
