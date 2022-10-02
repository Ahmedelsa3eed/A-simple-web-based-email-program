import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  private receiverSource = new BehaviorSubject<string>('');
  private subjectSource = new BehaviorSubject<string>('');
  private bodySource = new BehaviorSubject<string>('');

  constructor() { }

  getReceiver(): Observable<string> {
    return this.receiverSource.asObservable();
  }

  setReceiver(receiver: string): void {
    this.receiverSource.next(receiver);
  }

  getSubject(): Observable<string> {
    return this.subjectSource.asObservable();
  }

  setSubject(subject: string): void {
    this.subjectSource.next(subject);
  }

  getBody(): Observable<string> {
    return this.bodySource.asObservable();
  }

  setBody(body: string): void {
    this.bodySource.next(body);
  }
  
}
