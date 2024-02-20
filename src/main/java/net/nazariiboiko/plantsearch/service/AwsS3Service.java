package net.nazariiboiko.plantsearch.service;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {

    boolean saveFile(MultipartFile file, String fileName, String folderName);
    boolean deleteFile(String fileName, String folderName);
}
