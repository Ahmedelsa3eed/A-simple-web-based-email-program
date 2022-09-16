import { User } from 'src/app/models/User';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SigninService {

  private url = environment.baseUrl;

  constructor(private http: HttpClient) { }

  /**
   * The observe option specifies how much of the response to return
   * The responseType option specifies the format in which to return data
   */
  signIn(user: User): Observable<HttpResponse<User>> {
    return this.http.post<HttpResponse<User>>(`${this.url}/signIn`, user, {
      observe: "body",
      responseType: "json",
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
