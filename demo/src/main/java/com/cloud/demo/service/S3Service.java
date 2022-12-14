package com.cloud.demo.service;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.util.UUID;
@RequiredArgsConstructor
@Service
@Slf4j
public class S3Service implements FileService{

    public static final String ZAMBUC = "zambuc";
    private final AmazonS3Client amazonS3Client;



    @Override
    public String uploadFile(MultipartFile file){
        //upload file to AWS S3

        //prepare a Key
        var filenameExtension= StringUtils.getFilenameExtension(file.getOriginalFilename()) ;


        var key= UUID.randomUUID().toString()+"."+filenameExtension;
        var metaData= new ObjectMetadata();
        metaData.setContentLength(file.getSize());
        metaData.setContentType(file.getContentType());
        if(file.getContentType().equals("application/pdf")) {
            try {


                amazonS3Client.putObject(ZAMBUC, key, file.getInputStream(), metaData);

            } catch (IOException ioException) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An Exeception occured while uploading the PDF");
            }
        }else{
            throw new InvalidFileNameException("can not be different file",file.getOriginalFilename());
        }
        amazonS3Client.setObjectAcl(ZAMBUC,key, CannedAccessControlList.PublicRead);

        return amazonS3Client.getResourceUrl(ZAMBUC,key);

    }
}