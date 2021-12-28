package com.example.email.Server.emailContent;

import com.example.email.Server.controller.SingleTonServer;
import com.example.email.Server.folders.FolderFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.tomcat.util.http.fileupload.FileUploadBase.CONTENT_DISPOSITION;

public class FileResource {
    private static String DIRECTORY = "data/%s/attachments";
    /*
    * upload files, it accepts post request
    * we're trying to create some resource on the server
    * we return a list of those file names so that we can download them again
    * */
    public String uploadFiles(List<MultipartFile> multipartFiles, String emailAddress){
        DIRECTORY = String.format(DIRECTORY, emailAddress);
        String uniqueId = addAttachmentFile(DIRECTORY);
        try {
            for(MultipartFile file : multipartFiles) {
                String filename = StringUtils.cleanPath(file.getOriginalFilename());
                DIRECTORY += "/" + uniqueId;
                Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
                System.out.println(fileStorage);
                Files.copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            }
        }catch (IOException e){
            e.printStackTrace();

        }
        return uniqueId;
    }

    // Define a method to download files
//    @GetMapping("download/{filename}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {
        Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
        if(!Files.exists(filePath)) {
            throw new FileNotFoundException(filename + " was not found on the server");
        }
        Resource resource = (Resource) new UrlResource(filePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + ((UrlResource) resource).getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }

    private String addAttachmentFile(String path){
        String uniqueID = UUID.randomUUID().toString();
        FolderFactory folderFactory = new FolderFactory();
        folderFactory.createFolder(path+"/"+uniqueID);
        return uniqueID;
    }
}
