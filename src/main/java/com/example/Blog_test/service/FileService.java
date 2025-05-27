package com.example.Blog_test.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileService {

    public String uploadImage(String path, MultipartFile multipartFile) throws IOException {

        //filename
        String name = multipartFile.getOriginalFilename();

        //Generate Random Id
        String randomId = UUID.randomUUID().toString();
        String randomname = randomId.concat(name.substring(name.lastIndexOf(".")));


        //Fullpath
        String fullpath = path+ File.separator+randomname;

        //create folder if not created
        File file  = new File(path);
        if(!file.exists()){
            file.mkdir();
        }


        //copy the file
        Files.copy(multipartFile.getInputStream(), Paths.get(fullpath));

        return randomname;
    }

    public InputStream getResources(String path, String filename) throws FileNotFoundException {
        String fullpath = path+File.separator+filename;
        InputStream inputStream = new FileInputStream(fullpath);
        return inputStream;
    }

}
