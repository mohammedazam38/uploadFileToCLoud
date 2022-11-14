import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UploadPdfResponse } from './UploadPdfResponse';
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UploadPdfService {

  constructor(private httpClient: HttpClient) { }
  uploadPdf(fileEntry: File): Observable<UploadPdfResponse> {
    //https post call
    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name);
    return this.httpClient.post<UploadPdfResponse>("http://localhost:8080/api/upload", formData);

  }

  getAll(): Observable<Array<UploadPdfResponse>> {
    return this.httpClient.get<Array<UploadPdfResponse>>("http://localhost:8080/api/upload");
  }
}
