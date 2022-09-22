import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageWrapper {

  constructor() { }

  public saveUser(user: User) {
    localStorage.setItem('user', JSON.stringify(user));
  }

  public getUser() {
    return JSON.parse(localStorage.getItem('user')!);
  }

  public removeUser() {
    localStorage.removeItem('user');
  }

  public clearUser() {
    localStorage.clear();
  }

}
