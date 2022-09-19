import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Email } from '../models/Email';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class GetEmailsService {

  private url = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public requestEmails(user: User, endpoint: string): Observable<HttpResponse<Email[]>> {
    return this.http.get<Email[]>(`${this.url}/${endpoint}`, {
      observe: 'response',
      params: {
        userID: user._id,
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
