import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AttachmentService {

  private url = environment.baseUrl;

  constructor(private http: HttpClient) { }

  uploadFiles(multipartFiles: FormData, userID: string) {
    return this.http.post<boolean>(`${this.url}/upload`, multipartFiles, {
      observe: 'response',
      params: {
        userID: userID,
      },
      responseType: 'json'
    }).pipe(
      catchError(this.handleError)
    );
  }

  downloadFile(fileName: string, attachmentPosition:string): Observable<HttpResponse<Blob>> {
    return this.http.get(`${this.url}/download`, {
      observe: 'response',
      params: {
        attachmentName: fileName,
        attachmentPosition : attachmentPosition
      },
      responseType: 'blob'
    }).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('An error occurred:', error.error);
    } else {
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }

}
