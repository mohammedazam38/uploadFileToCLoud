package com.cloud.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadPdfResponse {
    @Id
    private String id;
    private String pdfUrl;
}
