import { User } from 'src/app/models/User';
import { HttpClient, HttpErrorResponse, HttpEvent, HttpResponse } from '@angular/common/http';
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

  /**
   * The observe option specifies how much of the response to return
   * The responseType option specifies the format in which to return data
   * @param user the request body
   * @param endpoint example endpoint: signIn or signUp
   * @returns Observable<HttpResponse<>>
   */
  validateUser(user: User, endpoint: string): Observable<HttpResponse<User>> {
    return this.http.post<User>(`${this.url}/${endpoint}`, user, {
      observe: 'response',
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  sendEmail(email: Email, user: User): Observable<HttpResponse<boolean>> {
    return this.http.post<boolean>(`${this.url}/send`, email, {
      observe: 'response',
      params: {
        userID: user._id,
        email: user.email
      },
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  uploadFiles(attachments: FormData) {
    return this.http.post<any>(`${this.url}/upload`, attachments, {
      observe: 'response',
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  downloadFile(fileName: string, user: User): Observable<HttpResponse<Blob>> {
    return this.http.get(`${this.url}/download`, {
      observe: 'response',
      params: {
        fileName: fileName,
        email: user.email
      },
      responseType: 'blob'
    }).pipe(
      catchError(this.handleError)
    );
  }

  search(userID: string, searchString: string, folderName: string): Observable<HttpResponse<Email[]>> {
    return this.http.get<Email[]>(`${this.url}/search`, {
      observe: 'response',
      params: {
        userID: userID,
        searchString: searchString,
        searchPosition: folderName
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

  deleteEmailFromInbox(email: Email): Observable<HttpResponse<boolean>> {
    return this.http.post<boolean>(`${this.url}/deleteFromInbox`, email, {
      observe: 'response',
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  deleteEmailFromDB(email: Email, position: string, user: User): Observable<HttpResponse<boolean>> {
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
