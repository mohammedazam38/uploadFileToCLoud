import { Component, ɵisSubscribable } from '@angular/core';
import { Router } from '@angular/router';
import { NgxFileDropEntry, FileSystemFileEntry, FileSystemDirectoryEntry } from 'ngx-file-drop';
import { UploadPdfService } from '../upload-pdf.service';
import { UploadPdfResponse } from '../UploadPdfResponse';

@Component({
  selector: 'app-upload-pdf',
  templateUrl: './upload-pdf.component.html',
  styleUrls: ['./upload-pdf.component.css']
})
export class UploadPdfComponent {

  public files: NgxFileDropEntry[] = [];
  fileUploaded: boolean = false;
  fileEntry: FileSystemFileEntry | undefined;
  allFile: boolean = false;
  isSuccess: boolean = false;
  uploadResponse!: UploadPdfResponse[];
  constructor(private uploadService: UploadPdfService, private router: Router) { }
  public dropped(files: NgxFileDropEntry[]) {
    this.files = files;
    for (const droppedFile of files) {

      // Is it a file?
      if (droppedFile.fileEntry.isFile) {
        this.fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        this.fileEntry.file((file: File) => {

          // Here you can access the real file
          console.log(droppedFile.relativePath, file);
          this.fileUploaded = true;
          /**
          // You could upload it like this:
          const formData = new FormData()
          formData.append('logo', file, relativePath)
          // Headers
          const headers = new HttpHeaders({
            'security-token': 'mytoken'
          })
          this.http.post('https://mybackend.com/api/upload/sanitize-and-save-logo', formData, { headers: headers, responseType: 'blob' })
          .subscribe(data => {
            // Sanitized logo returned from backend
          })
          **/

        });
      } else {
        // It was a directory (empty directories are added, otherwise only files)
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log(droppedFile.relativePath, fileEntry);
      }
    }
  }

  public fileOver(event: any) {
    console.log(event);
  }

  public fileLeave(event: any) {
    console.log(event);
  }
  public uploadPdf() {
    if (this.fileEntry !== undefined) {
      console.log(this.fileEntry);
      this.fileEntry.file(file => {
        this.uploadService.uploadPdf(file).subscribe(data => {
          console.log(data.id);
          this.isSuccess = true;
        })
      })

    }
  }
  allFiles() {
    this.uploadService.getAll().subscribe(data => {
      this.uploadResponse = data;
      this.allFile = true;
    })
  }
}