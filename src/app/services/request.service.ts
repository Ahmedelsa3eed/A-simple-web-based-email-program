import { User } from 'src/app/models/User';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Email } from '../models/Email';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  private url = environment.baseUrl;

  constructor(private http: HttpClient) { }

  validateUser(user: User, endpoint: string): Observable<HttpResponse<User>> {
    return this.http.post<User>(`${this.url}/${endpoint}`, user, {
      observe: 'response',
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  sendEmail(email: Email, user: User): Observable<HttpResponse<boolean>> {
    console.log("sendEmail() called.... with "+ email.attachments + " \\\ " + user);
    return this.http.post<boolean>(`${this.url}/send`, email, {
      observe: 'response',
      params: {
        userID: user._id,
      },
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }
  addToDraft(email: Email, user: User): Observable<HttpResponse<boolean>> {
    return this.http.post<boolean>(`${this.url}/addToDrafts`, email, {
      observe: 'response',
      params: {
        userID: user._id,
      },
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  search(userID: string, searchString: string, searchPosition: string): Observable<HttpResponse<Email[]>> {
    return this.http.get<Email[]>(`${this.url}/search`, {
      observe: 'response',
      params: {
        userID: userID,
        searchString: searchString,
        searchPosition: searchPosition
      },
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  sort(by: string, folderName: string, userId: string) {
    return this.http.get<Email[]>(`${this.url}/sort`, {
      observe: 'response',
      params: {
        sortBy: by,
        position: folderName,
        userID: userId
      },
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  deleteEmail(email: Email, position: string, user: User): Observable<HttpResponse<boolean>> {
    return this.http.delete<boolean>(`${this.url}/delete`, {
      observe: 'response',
      params: {
        userID: user._id,
        emailID: email._id,
        position: position
      },
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  public markAsSeen(emailID: string, userID: string): Observable<HttpResponse<boolean>> {
    console.log("markAsSeen() called \n"+ emailID + " \\\ " + userID);
    return this.http.delete<boolean>(`${this.url}/markAsSeen`,  {
      observe: 'response',
      params: {
        emailID: emailID,
        userID: userID
      },
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
      );
  }
  public undoRemoveFromInbox(emailID:string, userID:string): Observable<HttpResponse<boolean>> {
    return this.http.delete<boolean>(`${this.url}/undoRemoveFromInbox`, {
      observe: 'response',
      params: {
        emailID: emailID,
        userID: userID
      },
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }


}
