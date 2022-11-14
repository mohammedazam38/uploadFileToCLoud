package com.cloud.demo.service;

import com.cloud.demo.dto.UploadPdfResponse;
import com.cloud.demo.exception.InvalidFileException;
import com.cloud.demo.model.PDF;
import com.cloud.demo.repo.PdfRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PdfService {
    @Autowired
    private S3Service s3Service;
    @Autowired

    private PdfRepo pdfRepo;
    public UploadPdfResponse uploadPdf(MultipartFile file) throws InvalidFileException {

        var pdf= new PDF();

            String pdfUrl = s3Service.uploadFile(file);
            String originalFile = file.getOriginalFilename();
            String name = originalFile.substring(0, originalFile.length() - 4);

            pdf.setPdfUrl(pdfUrl);
            pdf.setName(makeHash(name));
            var savePdf = pdfRepo.save(pdf);


        return new UploadPdfResponse(pdf.getId(),pdf.getPdfUrl());
    }

    private String makeHash(String name)  {
        Random ranGen = new SecureRandom();
        byte[] randomno= new byte[8];
        ranGen.nextBytes(randomno);
        String str="";
        try{
             str = new String(randomno, "UTF8");
        }catch (Exception e){
            throw new RuntimeException();
        }
        return str+name ;

    }
   @Transactional(readOnly = true)
    public List<UploadPdfResponse> getAllUploads() {
        return pdfRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    private UploadPdfResponse mapToDto(PDF pdf){
      if(pdf==null){return  null;}
      UploadPdfResponse uploadPdfResponse= new UploadPdfResponse();
      uploadPdfResponse.setPdfUrl(pdf.getPdfUrl());
      uploadPdfResponse.setId(pdf.getId());
      return uploadPdfResponse;
    }
}
