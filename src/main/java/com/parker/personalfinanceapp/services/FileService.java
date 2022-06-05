package com.parker.personalfinanceapp.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    @Autowired
    AmazonS3 amazonS3;

    public List<String> getAllFileNames(Long userId) {
        ObjectListing objectListing = amazonS3.listObjects("pfpbucket123/", userId.toString());
        List<String> fileNames = new ArrayList<>();
        objectListing.getObjectSummaries()
                     .forEach(file -> fileNames.add(file.getKey()));
        return fileNames;
    }

    public void saveFile(Long userId, MultipartFile mpf) throws IOException {
        amazonS3.putObject("pfpbucket123/" + userId.toString(), mpf.getOriginalFilename(), mpf.getInputStream(),
                new ObjectMetadata());
    }

    public S3Object getFile(Long userId, String fileName) {
        String bucketName = "pfpbucket123";
        fileName = userId.toString() + '/' + fileName;
        return amazonS3.getObject(bucketName, fileName);
    }

    public void deleteFile(Long userId, String fileName) {
        String bucketName = "pfpbucket123";
        fileName = userId.toString() + '/' + fileName;
        amazonS3.deleteObject(bucketName, fileName);
    }
}
