package com.cloud.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="PDF")

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PDF {

    @Id
    private String id;
    private String name;

    private String pdfUrl;

}