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

  /**
   * The observe option specifies how much of the response to return
   * The responseType option specifies the format in which to return data
   * @param user the request body
   * @param endpoint example endpoint: signIn or signUp
   * @returns Observable<HttpResponse<>>
   */
  request(user: User, endpoint: string): Observable<HttpResponse<User>> {    
    return this.http.post<User>(`${this.url}/${endpoint}`, user, {
      observe: 'response',
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  sendEmail(email: Email): Observable<HttpResponse<boolean>> {
    return this.http.post<boolean>(`${this.url}/send`, email, {
      observe: 'response',
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
