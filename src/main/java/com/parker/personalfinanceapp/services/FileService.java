package com.parker.personalfinanceapp.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    @Autowired
    AmazonS3 amazonS3;

    public void saveFile(Long userId, MultipartFile mpf) throws IOException {
//        String fileDir = new File(System.getProperty("user.dir")) + "/files/" + userId;
//        File file = new File(fileDir + "/" + mpf.getOriginalFilename());
//        Path path = Paths.get(file.toString());
//        Files.createDirectories(path);
//        Files.copy(mpf.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        amazonS3.putObject("pfpbucket123/" + userId.toString(), mpf.getOriginalFilename(), mpf.getInputStream(),
                new ObjectMetadata());
    }

//    public MultipartFile getFile(Long userId, String fileName) {
//        String fileDir = new File(System.getProperty("user.dir")) + "/files/" + userId;
//        File file = new File(fileDir + "/" + fileName);
//        return Files.
//    }

    public void deleteFile(Long userId, String fileName) {

    }
}
