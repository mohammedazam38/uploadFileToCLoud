package com.cloud.demo.controller;


import com.cloud.demo.dto.UploadPdfResponse;
import com.cloud.demo.exception.InvalidFileException;
import com.cloud.demo.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor

public class UploadController {
    @Autowired
    private PdfService pdfService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UploadPdfResponse upload(@RequestParam("file") MultipartFile file) throws InvalidFileException {

        UploadPdfResponse up= pdfService.uploadPdf(file);
        return up;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UploadPdfResponse> getAllPdfLinks(){
        return pdfService.getAllUploads();
    }
}
