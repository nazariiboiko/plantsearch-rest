package net.example.plantsearchrest.repository;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.InputStream;

@Repository
@RequiredArgsConstructor
public class S3Repository {
    private final AmazonS3 amazonS3;

    public void uploadImage(String path, String fileName, InputStream inputStream, ObjectMetadata metadata) throws AmazonServiceException {
            amazonS3.putObject(new PutObjectRequest(path, fileName, inputStream, metadata));
    }

    public void deleteImage(String path, String fileName) throws AmazonServiceException{
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(path, fileName);
            amazonS3.deleteObject(deleteObjectRequest);
    }
}
