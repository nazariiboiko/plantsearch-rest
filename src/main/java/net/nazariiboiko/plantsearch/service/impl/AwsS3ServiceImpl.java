package net.nazariiboiko.plantsearch.service.impl;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nazariiboiko.plantsearch.repository.S3Repository;
import net.nazariiboiko.plantsearch.service.AwsS3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements AwsS3Service {

    private final S3Repository s3Rep;

    @Value("${s3.pictures.bucket}")
    private String bucketName;

    @Override
    public boolean saveFile(MultipartFile file, String fileName, String folderName) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            s3Rep.uploadImage(bucketName + folderName, fileName, file.getInputStream(), metadata);
            log.info("IN saveImageIntoS3 - file {} was successfully saved into folder {} in bucket {}", fileName, folderName, bucketName);
            return true;
        } catch (IOException exp) {
            return false;
        }
    }

    @Override
    public boolean deleteFile(String fileName, String folderName) {
        throw new UnsupportedOperationException();
    }
}
