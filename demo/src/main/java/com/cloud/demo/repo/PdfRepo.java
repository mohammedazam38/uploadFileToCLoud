package com.cloud.demo.repo;


import com.cloud.demo.model.PDF;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfRepo extends MongoRepository<PDF,String> {
}
